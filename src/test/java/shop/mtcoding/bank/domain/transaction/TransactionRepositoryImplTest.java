package shop.mtcoding.bank.domain.transaction;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import shop.mtcoding.bank.config.dummy.DummyObject;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
@DataJpaTest // DB 관련된 Bean이 다 올라온다.
class TransactionRepositoryImplTest extends DummyObject {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        autoincrementReset();
        dataSetting();
        em.clear(); // repository 테스트에서는 필수!
    }

    @Test
    @DisplayName("거래 내역 리스트 조회1")
    void transaction_list_test() {
        // given
        List<Transaction> all = transactionRepository.findAll();
        all.forEach((t) -> {
            System.out.println("테스트 t.getId() = " + t.getId());
            System.out.println("테스트 t.getSender() = " + t.getSender());
            System.out.println("테스트 t.getReceiver() = " + t.getReceiver());
            System.out.println("테스트 t.getGubun() = " + t.getGubun());
            System.out.println("=====================");
        });
    }

    @Test
    @DisplayName("거래 내역 리스트 조회2")
    void transaction_list_test2() {
        // given
        List<Transaction> all = transactionRepository.findAll();
        all.forEach((t) -> {
            System.out.println("테스트 t.getId() = " + t.getId());
            System.out.println("테스트 t.getSender() = " + t.getSender());
            System.out.println("테스트 t.getReceiver() = " + t.getReceiver());
            System.out.println("테스트 t.getGubun() = " + t.getGubun());
            System.out.println("=====================");
        });
    }
    @Test
    @DisplayName("'ALL'을 줬을 때 findTransactionList 테스트")
    void findTransaction_all_test() {
        // given
        Long accountId = 1L;

        // when
        List<Transaction> all = transactionRepository.findTransactionList(accountId, "ALL", 0);
        all.forEach((t) -> {
            System.out.println("t.getId() = " + t.getId());
            System.out.println("t.getAmount() = " + t.getAmount());
            System.out.println("t.getSender() = " + t.getSender());
            System.out.println("t.getReceiver() = " + t.getReceiver());
            System.out.println("t.getGubun() = " + t.getGubun());
            System.out.println("t.getWithdrawAccountBalance() = " + t.getWithdrawAccountBalance());
            System.out.println("t.getDepositAccountBalance() = " + t.getDepositAccountBalance());
            System.out.println("=============");
        });
    }

    @Test
    @DisplayName("'WITHDRAW'을 줬을 때 findTransactionList 테스트")
    void findTransaction_withdraw_test() {
        // given
        Long accountId = 1L;

        // when
        List<Transaction> all = transactionRepository.findTransactionList(accountId, "WITHDRAW", 0);
        all.forEach((t) -> {
            System.out.println("t.getId() = " + t.getId());
            System.out.println("t.getAmount() = " + t.getAmount());
            System.out.println("t.getSender() = " + t.getSender());
            System.out.println("t.getReceiver() = " + t.getReceiver());
            System.out.println("t.getGubun() = " + t.getGubun());
            System.out.println("t.getWithdrawAccountBalance() = " + t.getWithdrawAccountBalance());
            System.out.println("t.getDepositAccountBalance() = " + t.getDepositAccountBalance());
            System.out.println("=============");
        });
    }

    @Test
    @DisplayName("'DEPOSIT'을 줬을 때 findTransactionList 테스트")
    void findTransaction_deposit_test() {
        // given
        Long accountId = 1L;

        // when
        List<Transaction> all = transactionRepository.findTransactionList(accountId, "DEPOSIT", 0);
        all.forEach((t) -> {
            System.out.println("t.getId() = " + t.getId());
            System.out.println("t.getAmount() = " + t.getAmount());
            System.out.println("t.getSender() = " + t.getSender());
            System.out.println("t.getReceiver() = " + t.getReceiver());
            System.out.println("t.getGubun() = " + t.getGubun());
            System.out.println("t.getWithdrawAccountBalance() = " + t.getWithdrawAccountBalance());
            System.out.println("t.getDepositAccountBalance() = " + t.getDepositAccountBalance());
            System.out.println("=============");
        });
    }

    @Test
    @DisplayName("fetch join 테스트")
    void fetch_join_test() {
        // given
        Long accountId = 2L;

        // when
        List<Transaction> all = transactionRepository.findTransactionList(accountId, "WITHDRAW", 0);
        all.forEach((t) -> {
            System.out.println("t.getId() = " + t.getId());
            System.out.println("t.getAmount() = " + t.getAmount());
            System.out.println("t.getSender() = " + t.getSender());
            System.out.println("t.getReceiver() = " + t.getReceiver());
            System.out.println("t.getGubun() = " + t.getGubun());
            System.out.println("t.getWithdrawAccount().getBalance() = " + t.getWithdrawAccount().getBalance());
            System.out.println("출금 계좌의 fullname = " + t.getWithdrawAccount().getUser().getFullname());
            System.out.println("t.getWithdrawAccountBalance() = " + t.getWithdrawAccountBalance());
            System.out.println("t.getDepositAccountBalance() = " + t.getDepositAccountBalance());
            System.out.println("=============");
        });

        // then
        assertThat(all.get(0).getWithdrawAccountBalance()).isEqualTo(1100L);
    }
    private void dataSetting() {
        User ssar = userRepository.save(newUser("ssar", "쌀"));
        User cos = userRepository.save(newUser("cos", "코스"));
        User love = userRepository.save(newUser("love", "러브"));
        User admin = userRepository.save(newUser("admin", "관리자"));

        Account ssarAccount1 = accountRepository.save(newAccount(1111L, ssar));
        Account cosAccount = accountRepository.save(newAccount(2222L, cos));
        Account loveAccount = accountRepository.save(newAccount(3333L, love));
        Account ssarAccount2 = accountRepository.save(newAccount(4444L, ssar));

        Transaction withdrawTransaction1 = transactionRepository
                .save(newWithdrawTransaction(ssarAccount1, accountRepository));
        Transaction depositTransaction1 = transactionRepository
                .save(newDepositTransaction(cosAccount, accountRepository));
        Transaction transferTransaction1 = transactionRepository
                .save(newTransferTransaction(ssarAccount1, cosAccount, accountRepository));
        Transaction transferTransaction2 = transactionRepository
                .save(newTransferTransaction(ssarAccount1, loveAccount, accountRepository));
        Transaction transferTransaction3 = transactionRepository
                .save(newTransferTransaction(cosAccount, ssarAccount1, accountRepository));
    }

    private void autoincrementReset() {

        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE account_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE transaction_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }

}