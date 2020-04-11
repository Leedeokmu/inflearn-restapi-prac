package project.restapi.demoprojectforrestapi.events;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import project.restapi.demoprojectforrestapi.accounts.Account;
import project.restapi.demoprojectforrestapi.accounts.AccountAdapter;
import project.restapi.demoprojectforrestapi.accounts.CurrentUser;
import project.restapi.demoprojectforrestapi.common.ErrorsResource;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class EventController {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final EventValidator eventValidator;

    @PostMapping
    public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, Errors errors, @CurrentUser Account account) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }
        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        Event event = modelMapper.map(eventDto, Event.class);
        event.setManager(account);

        event.update();

        Event newEvent = eventRepository.save(event);
        ControllerLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(newEvent.getId());
        URI uri = selfLinkBuilder.toUri();
        event.setId(10);

        EventResource eventResource = new EventResource(event);
        eventResource.add(selfLinkBuilder.withRel("update-event")); // TODO : EventResource 내에 작성
        eventResource.add(linkTo(EventController.class).withRel("query-events"));
        eventResource.add(new Link("/docs/index.html#resources-events-create").withRel("profile"));
        return ResponseEntity.created(uri).body(eventResource);

    }

    @GetMapping
    public ResponseEntity queryEvents(
            Pageable pageable
            , PagedResourcesAssembler<Event> assembler
            , @CurrentUser Account account
    ) {
        Page<Event> events = this.eventRepository.findAll(pageable);
        PagedResources<Resource<Event>> resources = assembler.toResource(events, e -> new EventResource(e));
        resources.add(new Link("/docs/index.html#resources-events-list").withRel("profile"));
        if(account != null){
            resources.add(linkTo(EventController.class).withRel("create-event"));
        }
        return ResponseEntity.ok().body(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity getEvent(@PathVariable("id") Integer id, @CurrentUser Account currentUser) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Event foundEvent = optionalEvent.get();
        EventResource eventResource = new EventResource(foundEvent);
        eventResource.add(new Link("/docs/index.html#resources-events-get").withRel("profile"));
        if (foundEvent.getManager().equals(currentUser)) {
            eventResource.add(linkTo(EventController.class).slash(foundEvent.getId()).withRel("update-event"));
        }

        return ResponseEntity.ok().body(eventResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateEvent(
            @PathVariable("id") Integer id
            , @RequestBody @Valid EventDto eventDto
            , Errors errors,
            @CurrentUser Account currentUser) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if(optionalEvent.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        if (errors.hasErrors()) {
            return badRequest(errors);
        }
        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        Event existingEvent = optionalEvent.get();
        if(!existingEvent.getManager().equals(currentUser)){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        modelMapper.map(eventDto, existingEvent);
        Event savedEvent = this.eventRepository.save(existingEvent);

        EventResource eventResource = new EventResource(savedEvent);
        eventResource.add(new Link("/docs/index.html#resources-events-update").withRel("profile"));
        return ResponseEntity.ok().body(eventResource);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }
}
