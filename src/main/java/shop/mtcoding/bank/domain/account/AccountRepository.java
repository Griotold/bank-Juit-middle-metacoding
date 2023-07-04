package shop.mtcoding.bank.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    // todo : 리팩토링해야함
    Optional<Account> findByNumber(Long number);

    // select * from account where user.id = :id
    List<Account> findByUser_id(Long id);
}
