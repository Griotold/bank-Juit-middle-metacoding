package shop.mtcoding.bank.config.dummy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;

@Configuration
public class DummyDevInit extends DummyObject{
    @Profile("dev")
    @Bean
    CommandLineRunner init(UserRepository userRepository,
                           AccountRepository accountRepository) {
        return (args) -> {
            User ssar = userRepository.save(newUser("ssar", "쌀"));
            Account account1 = accountRepository.save(newAccount(1111L, ssar));
            Account account2 = accountRepository.save(newAccount(2222L, ssar));
        };
    }
}
