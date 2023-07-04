package shop.mtcoding.bank.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    // 해결 : Account를 가져올 때 User를 가져오지 않기 떄문에
    // fetch join으로 리팩토링 불필요
    Optional<Account> findByNumber(Long number);

    // select * from account where user.id = :id
    List<Account> findByUser_id(Long id);
}
