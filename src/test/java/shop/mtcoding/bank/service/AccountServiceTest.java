package shop.mtcoding.bank.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.mtcoding.bank.config.dummy.DummyObject;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.transaction.Transaction;
import shop.mtcoding.bank.domain.transaction.TransactionEnum;
import shop.mtcoding.bank.domain.transaction.TransactionRepository;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.account.AccountReqDto;
import shop.mtcoding.bank.dto.account.AccountRespDto;
import shop.mtcoding.bank.handler.ex.CustomApiException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static shop.mtcoding.bank.dto.account.AccountReqDto.*;
import static shop.mtcoding.bank.dto.account.AccountRespDto.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest extends DummyObject {
    @InjectMocks
    private AccountService accountService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Spy
    private ObjectMapper om;

    @Test
    @DisplayName("계좌 등록 테스트")
    void register_test() throws JsonProcessingException {
        // given
        Long userId = 1L;

        AccountSaveReqDto accountSaveReqDto = new AccountSaveReqDto();
        accountSaveReqDto.setNumber(1111L);
        accountSaveReqDto.setPassword(1234L);

        // stub 1
        User ssar = newMockUser(userId, "ssar", "쌀");
        when(userRepository.findById(any())).thenReturn(Optional.of(ssar));

        // stub 2
        when(accountRepository.findByNumber(any())).thenReturn(Optional.empty());

        // stub 3
        Account ssarAccount = newMockAccount(1L, 1111L, 1000L, ssar);
        when(accountRepository.save(any())).thenReturn(ssarAccount);

        // when
        AccountSaveRespDto accountSaveRespDto = accountService.register(accountSaveReqDto, userId);
        String responseBody = om.writeValueAsString(accountSaveRespDto);
        System.out.println("테스트 = " + responseBody);

        // then
        assertThat(accountSaveRespDto.getNumber()).isEqualTo(1111L);
    }
    @Test
    @DisplayName("본인계좌목록 조회 서비스 ")
    void accountList_test() throws JsonProcessingException {
        // given
        Long userId = 1L;

        // stub 1
        User ssar = newMockUser(userId, "ssar", "쌀");
        when(userRepository.findById(any())).thenReturn(Optional.of(ssar));

        // stub 2
        Account account1 = newMockAccount(1L, 1111L, 1000L, ssar);
        Account account2 = newMockAccount(2L, 2222L, 1000L, ssar);
        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);
        when(accountRepository.findByUser_id(any())).thenReturn(accounts);

        // when
        AccountListRespDto accountListRespDto = accountService.accountList(userId);
        String responseBody = om.writeValueAsString(accountListRespDto);
        System.out.println("테스트 = " + responseBody);

        // then
        assertThat(accountListRespDto.getFullname()).isEqualTo("쌀");
        assertThat(accountListRespDto.getAccounts().size()).isEqualTo(2L);
        assertThat(accountListRespDto.getAccounts().get(0).getNumber()).isEqualTo(1111L);
        assertThat(accountListRespDto.getAccounts().get(1).getNumber()).isEqualTo(2222L);
    }
    @Test
    @DisplayName("계좌 삭제 서비스 테스트")
    void deleteAccount_test() {
        // given
        long number = 1111L;
        long userId = 2L;

        // stub 1
        User ssar = newMockUser(1L, "ssar", "쌀");
        Account ssarAccount = newMockAccount(1L, 1111L, 1000L, ssar);
        when(accountRepository.findByNumber(any())).thenReturn(Optional.of(ssarAccount));

        // when

        // then
        assertThrows(CustomApiException.class, () -> accountService.deleteAccount(number, userId));

    }
    /**
     * Account -> balance가 변경 됐는지
     * Transaction -> balance가 잘 기록되었는지
     * */
    @Test
    @DisplayName("계좌 입금 서비스 테스트")
    void deposit_service_test() {
        // given
        AccountDepositReqDto accountDepositReqDto = new AccountDepositReqDto();
        accountDepositReqDto.setNumber(1111L);
        accountDepositReqDto.setAmount(100L);
        accountDepositReqDto.setGubun("DEPOSIT");
        accountDepositReqDto.setTel("01034567890");

        // stub 1 - accountRepository.findByNumber() 동작시 기대값
        User ssar = newMockUser(1L, "ssar", "쌀");
        Account ssarAccount1 = newMockAccount(1L, 1111L, 1000L, ssar);
        when(accountRepository.findByNumber(any())).thenReturn(Optional.of(ssarAccount1));

        // stub 2 - transactionRepository.save() 호출시 기댓값
        Account ssarAccount2 = newMockAccount(1L, 1111L, 1000L, ssar);
        Transaction transaction = newMockDepositTransaction(1L, ssarAccount2);
        when(transactionRepository.save(any())).thenReturn(transaction);

        // when
        AccountDepositRespDto accountDepositRespDto = accountService.deposit(accountDepositReqDto);
        System.out.println("트랜잭션 입금 계좌 잔액 = " + accountDepositRespDto.getTransaction().getDepositAccountBalance());
        System.out.println("계좌쪽 잔액 = " + ssarAccount1.getBalance());

        // then
        assertThat(accountDepositRespDto.getNumber()).isEqualTo(1111L);
        assertThat(accountDepositRespDto.getTransaction().getAmount()).isEqualTo(100L);
        assertThat(ssarAccount1.getBalance()).isEqualTo(1100L);
        assertThat(accountDepositRespDto.getTransaction().getDepositAccountBalance()).isEqualTo(1100L);
    }

    @Test
    @DisplayName("합리적인 계좌 입금 서비스 테스트")
    public void 계좌입금_test3() throws Exception {
        // given
        Account account = newMockAccount(1L, 1111L, 1000L, null);
        Long amount = 100L;

        // when
        if (amount <= 0L) {
            throw new CustomApiException("0원 이하의 금액을 입금할 수 없습니다");
        }
        account.deposit(amount);

        // then
        assertThat(account.getBalance()).isEqualTo(1100L);
    }
    @Test
    @DisplayName("계좌 출금 서비스 테스트 ")
    void withdraw_test() {
        // given
        Long amount = 100L;
        Long password = 1234L;
        Long userId = 1L;

        User ssar = newMockUser(1L, "ssar", "쌀");
        Account ssarAccount = newMockAccount(1L, 1111L, 1000L, ssar);

        // when
        // 0원 체크
        if (amount <= 0L) {
            throw new CustomApiException("0원 이하의 금액을 입금할 수 없습니다.");
        }

        // 출금 소유자 확인
        ssarAccount.checkOwner(userId);

        //계좌 비밀번호 테스트
        ssarAccount.checkSamePassword(password);

        // 계좌 잔액 체크
//        ssarAccount.checkBalance(amount);

        // 출금 확인
        ssarAccount.withdraw(amount);

        // then
        assertThat(ssarAccount.getBalance()).isEqualTo(900L);

    }
}