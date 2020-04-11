package project.restapi.demoprojectforrestapi.index;

import org.junit.jupiter.api.Test;
import project.restapi.demoprojectforrestapi.common.BaseTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IndexTest extends BaseTest {

    @Test
    public void index () throws Exception {
        // given
        // when
        this.mockMvc.perform(get("/api/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.event").exists())
        ;
        // then
    }


}