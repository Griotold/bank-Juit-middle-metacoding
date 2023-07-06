package shop.mtcoding.bank.web;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.bank.config.dummy.DummyObject;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.account.AccountReqDto;
import shop.mtcoding.bank.handler.ex.CustomApiException;

import javax.persistence.EntityManager;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static shop.mtcoding.bank.dto.account.AccountReqDto.*;

@Sql("classpath:db/teardown.sql")
@ActiveProfiles("test")
//@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
class AccountControllerTest extends DummyObject {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        User ssar = userRepository.save(newUser("ssar", "쌀"));
        User cos = userRepository.save(newUser("cos", "코스"));
        Account account1 = accountRepository.save(newAccount(1111L, ssar));
        Account account2 = accountRepository.save(newAccount(2222L, ssar));
        Account cos_account = accountRepository.save(newAccount(3333L, cos));

        em.clear();
    }

    // setBefore = TEST_METHOD : setUp()메소드 수행전에
    // setBefore = TEST_EXECUTION : saveAccount_test() 수행전에
    @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION) // 디비에서 ssar을 조회해서 세션에 담아주는 어노테이션
    @Test
    @DisplayName("컨트롤러의 saveAccount() 테스트")
    void saveAccount_test() throws Exception {
        // given
        AccountSaveReqDto accountSaveReqDto = new AccountSaveReqDto();
        accountSaveReqDto.setNumber(9999L);
        accountSaveReqDto.setPassword(1234L);
        String requestBody = om.writeValueAsString(accountSaveReqDto);
        System.out.println("테스트 = " + requestBody);

        // when
        ResultActions resultActions
                = mvc.perform(post("/api/s/account")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println("테스트 = " + responseBody);

        // then
        resultActions.andExpect(status().isCreated());
    }
    @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    @DisplayName("본인계좌목록보기 컨트롤러 테스트")
    void findUserAccountListV3_test() throws Exception {
        // given

        // when
        ResultActions resultActions
                = mvc.perform(get("/api/s/account/login-user"));
        String responseBody = resultActions
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println("테스트 = " + responseBody);

        // then
        resultActions.andExpect(status().isOk());
    }
    /**
     * 테스트시에는 insert 한것들이 전부 PC에 올라간다(영속화)
     * 영속화된 것을 초기화 해주는 것이 중요
     * */
    @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    @DisplayName("계좌 삭제 컨트롤러 테스트")
    void deleteAccount_test() throws Exception {
        // given
        long number = 1111L;

        // when
        ResultActions resultActions
                = mvc.perform(delete("/api/s/account/" + number));
        String responseBody = resultActions
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println("테스트 = " + responseBody);

        // then
        // Junit 테스트에서 delete 쿼리는 DB관련(DML)이므로 가장 마지막에 실행되면 발동 안됨
        assertThrows(CustomApiException.class, () -> accountRepository.findByNumber(number)
                .orElseThrow(() -> new CustomApiException("계좌를 찾을 수 없습니다.")));

    }
    @Test
    @DisplayName("계좌 입금 컨트롤러 테스트")
    void deposit_test() throws Exception {
        // given
        AccountDepositReqDto accountDepositReqDto = new AccountDepositReqDto();
        accountDepositReqDto.setNumber(1111L);
        accountDepositReqDto.setAmount(100L);
        accountDepositReqDto.setGubun("DEPOSIT");
        accountDepositReqDto.setTel("01034567890");

        String requestBody = om.writeValueAsString(accountDepositReqDto);
        System.out.println("requestBody = " + requestBody);

        // when
        ResultActions resultActions
                = mvc.perform(post("/api/account/deposit")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);

        // then
        resultActions.andExpect(status().isCreated());
    }
}