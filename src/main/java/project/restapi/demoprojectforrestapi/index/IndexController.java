package project.restapi.demoprojectforrestapi.index;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.restapi.demoprojectforrestapi.events.EventController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class IndexController {
    @GetMapping("/api")
    public ResourceSupport index() {
        ResourceSupport index  = new ResourceSupport();
        index.add(linkTo(EventController.class).withRel("event"));
        return index;
    }
}
