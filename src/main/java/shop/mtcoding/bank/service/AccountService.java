package shop.mtcoding.bank.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.account.AccountReqDto;
import shop.mtcoding.bank.dto.account.AccountRespDto;
import shop.mtcoding.bank.handler.ex.CustomApiException;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static shop.mtcoding.bank.dto.account.AccountReqDto.*;
import static shop.mtcoding.bank.dto.account.AccountRespDto.*;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AccountService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public AccountSaveRespDto register(AccountSaveReqDto accountSaveReqDto,
                                                      Long userId) {
        // User가 DB에 있는지 검증
        User userPS = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException("유저를 찾을 수 없습니다.")
        );

        // 중복 여부 체크 : 해당 계좌가 DB에 있는지
        Optional<Account> accountOP = accountRepository.findByNumber(accountSaveReqDto.getNumber());
        if (accountOP.isPresent()) {
            throw new CustomApiException("해당 계좌가 이미 존재합니다.");
        }

        // 계좌 등록
        Account account = accountSaveReqDto.toEntity(userPS);
        Account accountPS = accountRepository.save(account);

        // DTO를 응답
        return new AccountSaveRespDto(accountPS);
    }

    /**
     * 계좌목록 보기 유저별
     * */
    public AccountListRespDto accountList(Long userId) {
        // User가 DB에 있는지 검증
        User userPS = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException("유저를 찾을 수 없습니다."));

        // 유저의 모든 계좌목록
        List<Account> accountListPS = accountRepository.findByUser_id(userId);
        return new AccountListRespDto(userPS, accountListPS);

    }
}
