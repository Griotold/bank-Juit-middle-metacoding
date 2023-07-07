package shop.mtcoding.bank.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import shop.mtcoding.bank.config.dummy.DummyObject;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.transaction.Transaction;
import shop.mtcoding.bank.domain.transaction.TransactionRepository;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;

import javax.persistence.EntityManager;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Sql("classpath:db/teardown.sql")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
class TransactionControllerTest extends DummyObject {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        dataSetting();
        em.clear();
    }
    @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION) // 디비에서 ssar을 조회해서 세션에 담아주는 어노테이션
    @Test
    @DisplayName("입출금목록보기 컨트롤러 테스트 1")
    void transactionList_test1() throws Exception {
        // given
        Long number = 1111L;
        String gubun = "ALL";
        String page = "0";

        // when
        ResultActions resultActions
                = mvc.perform(get("/api/s/account/"+number+"/transaction")
                .param("gubun", gubun).param("page", page));

        String responseBody = resultActions
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println("responseBody = " + responseBody);
        // then
        resultActions.andExpect(jsonPath("$.data.transactions[0].balance").value(900L));
    }
    @WithUserDetails(value = "cos", setupBefore = TestExecutionEvent.TEST_EXECUTION) // 디비에서 ssar을 조회해서 세션에 담아주는 어노테이션
    @Test
    @DisplayName("입출금목록보기 컨트롤러 테스트 2")
    void transactionList_test2() throws Exception {
        // given
        Long number = 2222L;
        String gubun = "ALL";
        String page = "0";

        // when
        ResultActions resultActions
                = mvc.perform(get("/api/s/account/"+number+"/transaction")
                .param("gubun", gubun).param("page", page));

        String responseBody = resultActions
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println("responseBody = " + responseBody);
        // then
        resultActions.andExpect(jsonPath("$.data.transactions[2].balance").value(1100L));
        resultActions.andExpect(jsonPath("$.data.transactions[0].tel").value("01022227777"));

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
}