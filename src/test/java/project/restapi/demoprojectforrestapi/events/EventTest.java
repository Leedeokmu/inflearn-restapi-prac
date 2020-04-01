package project.restapi.demoprojectforrestapi.events;

import org.assertj.core.api.Assertions;
import org.junit.Test;


public class EventTest {
    @Test
    public void builder () {
        // given
        Event event = Event.builder()
                .name("Inflearn Spring REST API")
                .description("REST API Development with Spring")
                .build();
        Assertions.assertThat(event).isNotNull();
    }

}