package shop.mtcoding.bank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.transaction.Transaction;
import shop.mtcoding.bank.domain.transaction.TransactionRepository;
import shop.mtcoding.bank.dto.transaction.TransactionRespDto;
import shop.mtcoding.bank.handler.ex.CustomApiException;

import java.util.List;

import static shop.mtcoding.bank.dto.transaction.TransactionRespDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionListRespDto transactionList(Long userId, Long accountNumber, String gubun, int page) {
        Account accountPS = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new CustomApiException("해당 계좌를 찾을 수 없습니다."));

        // 해당 계좌가 로그인 된 유저 것인지 체크
        accountPS.checkOwner(userId);

        List<Transaction> transactionListPS = transactionRepository.findTransactionList(accountPS.getId(), gubun, page);
        return new TransactionListRespDto(accountPS, transactionListPS);
    }

}
