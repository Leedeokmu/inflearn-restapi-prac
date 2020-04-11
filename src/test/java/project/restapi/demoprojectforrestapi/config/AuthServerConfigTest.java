package project.restapi.demoprojectforrestapi.config;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import project.restapi.demoprojectforrestapi.accounts.AccountService;
import project.restapi.demoprojectforrestapi.common.AppProperties;
import project.restapi.demoprojectforrestapi.common.BaseControllerTest;
import project.restapi.demoprojectforrestapi.common.TestDescription;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthServerConfigTest extends BaseControllerTest {
    @Autowired
    AccountService accountService;
    @Autowired
    AppProperties appProperties;

    @Test
    @TestDescription("인증 토큰을 발급 받는 테스트")
    public void getAuthToken () throws Exception {
        // when & then
        this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                .param("username", appProperties.getUserUsername())
                .param("password", appProperties.getUserPassword())
                .param("grant_type", "password")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("access_token").isNotEmpty())
                .andExpect(jsonPath("refresh_token").isNotEmpty())
                .andExpect(jsonPath("token_type").value("bearer"))
                .andExpect(jsonPath("expires_in").isNumber())
        ;
    }

}