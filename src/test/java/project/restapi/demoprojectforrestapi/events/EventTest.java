package project.restapi.demoprojectforrestapi.events;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

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

    @ParameterizedTest
    @CsvSource({
            "0,0,true",
            "100,0,false",
            "0,100,false"
    })
    public void testFree (int basePrice, int maxPrice, boolean isFree) {
        // given
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();
        // when
        event.update();
        // then
        assertThat(event.isFree()).isEqualTo(isFree);
    }

    @ParameterizedTest
    @MethodSource("parametersForTestOffline")
    public void testOffline (String location, boolean isOffline) {
        // given
        Event event = Event.builder()
                .location(location)
                .build();
        // when
        event.update();
        // then
        assertThat(event.isOffline()).isEqualTo(isOffline);
    }

    static Stream<Arguments> parametersForTestOffline() {
        return Stream.of(
                arguments("강남", true),
                arguments(null, false),
                arguments("   ", false)
        );
    }

}