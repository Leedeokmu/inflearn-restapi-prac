package project.restapi.demoprojectforrestapi.events;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class EventTest {
    @Test
    public void builder () {
        // given
        Event event = Event.builder()
                .name("Inflearn Spring REST API")
                .description("REST API Development with Spring")
                .build();
        assertThat(event).isNotNull();
    }

    @Test
    public void testFree () {
        // given
        Event event = Event.builder()
                .basePrice(0)
                .maxPrice(0)
                .build();
        // when
        event.update();

        // then
        assertThat(event.isFree()).isTrue();

        // given
        event = Event.builder()
                .basePrice(100)
                .maxPrice(0)
                .build();
        // when
        event.update();

        // then
        assertThat(event.isFree()).isFalse();

        // given
        event = Event.builder()
                .basePrice(0)
                .maxPrice(100)
                .build();
        // when
        event.update();

        // then
        assertThat(event.isFree()).isFalse();

    }

    @Test
    public void testOffline () {
        // given
        Event event = Event.builder()
                .location("강남역 네이버 D2")
                .build();
        // when
        event.update();

        // then
        assertThat(event.isOffline()).isTrue();

        // given
        event = Event.builder()
                .build();
        // when
        event.update();

        // then
        assertThat(event.isOffline()).isFalse();
    }

}