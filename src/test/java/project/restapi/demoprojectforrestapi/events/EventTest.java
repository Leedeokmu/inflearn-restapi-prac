package project.restapi.demoprojectforrestapi.events;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(JUnitParamsRunner.class)
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
    @Parameters({
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

    @Test
    @Parameters
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

    private Object[] parametersForTestOffline() {
        return new Object[]{
                new Object[]{"강남", true},
                new Object[]{null, false},
                new Object[]{"   ", false}
        };
    }

}