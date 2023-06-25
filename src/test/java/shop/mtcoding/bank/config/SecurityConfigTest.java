package shop.mtcoding.bank.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureMockMvc // Mock 환경에 MockMvc가 등록됨
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) // 가짜 환경
class SecurityConfigTest {
    // Mock 환경에 등록된 MockMvc 주입
    @Autowired
    private MockMvc mvc;
    @Test
    @DisplayName("인증 테스트")
    void authentication_test() throws Exception{
        // given

        // when
        ResultActions resultActions = mvc.perform(get("/api/s/blahblah"));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        int status = resultActions.andReturn().getResponse().getStatus();

        System.out.println("responseBody = " + responseBody);
        System.out.println("status = " + status);

        // then
    }
    @Test
    @DisplayName("인가 테스트")
    void authorization_test() throws Exception {
        // given

        // when

        // then
    }
}