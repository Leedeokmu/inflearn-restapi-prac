package project.restapi.demoprojectforrestapi.accounts;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

    @Test
    public void findByUsername () {
        // given
        String email = "deokmu@email.com";
        String password = "deokmu";
        Account account = Account.builder()
                .email(email)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();

        Account saveAccount = accountRepository.save(account);
        // when
        UserDetailsService userDetailsService = accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // then
        assertThat(userDetails.getPassword()).isEqualTo(password);
    }

    @Test
    public void findByUsernameFail () {
        // given
        String username = "random@email.com";
        // then
        expectedException.expect(UsernameNotFoundException.class);
        expectedException.expectMessage(Matchers.containsString(username));
        //when
        accountService.loadUserByUsername(username);
    }
}