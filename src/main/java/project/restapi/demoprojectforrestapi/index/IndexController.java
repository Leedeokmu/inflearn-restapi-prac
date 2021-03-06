package project.restapi.demoprojectforrestapi.index;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.restapi.demoprojectforrestapi.events.EventController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class IndexController {
    @GetMapping("/api")
    public RepresentationModel index() {
        RepresentationModel index  = new RepresentationModel();
        index.add(linkTo(EventController.class).withRel("event"));
        return index;
    }
}
