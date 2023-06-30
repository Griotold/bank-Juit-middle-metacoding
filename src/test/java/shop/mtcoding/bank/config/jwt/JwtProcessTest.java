package shop.mtcoding.bank.config.jwt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shop.mtcoding.bank.config.auth.LoginUser;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class JwtProcessTest {

    @Test
    @DisplayName("토큰 생성 테스트")
    void create_test() {
        // given
        User user = User.builder().id(1L).role(UserEnum.CUSTOMER).build();
        LoginUser loginUser = new LoginUser(user);

        // when
        String jwtToken = JwtProcess.create(loginUser);
        System.out.println("jwtToken = " + jwtToken);

        // then
        assertThat(jwtToken).startsWith(JwtVO.TOKEN_PREFIX);
    }
    @Test
    @DisplayName("토큰 검증")
    void verify_test() {
        // given
        String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiYW5rIiwicm9sZSI6IkNVU1RPTUVSIiwiaWQiOjEsImV4cCI6MTY4ODcyMDczNX0.fwtkqUCbUjvLW2xsMthDzwnmNuaql96Ltv3nEv-hZNvFyxDbPGpTaqeXM6Gj7Ke70F32irvgYAOHu2TKlKnCBQ";

        // when
        LoginUser loginUser = JwtProcess.verify(jwtToken);
        Long id = loginUser.getUser().getId();
        String role = loginUser.getUser().getRole().name();
        System.out.println("id = " + id);
        System.out.println("role = " + role);

        // then
        assertThat(id).isEqualTo(1L);
        assertThat(role).isEqualTo(UserEnum.CUSTOMER.name());
    }

}