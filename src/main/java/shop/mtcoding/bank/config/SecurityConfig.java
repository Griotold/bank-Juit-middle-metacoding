package shop.mtcoding.bank.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import shop.mtcoding.bank.domain.user.UserEnum;

@Configuration
public class SecurityConfig {

    // @Slf4j를 쓰면 테스트시 문제가 생겨서 이 방식으로
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        log.debug("디버그: BCryptPasswordEncoder 빈 등록됨");
        return new BCryptPasswordEncoder();
    }

    // todo : JWT 필터 등록이 필요함

    /**
     * JWT 서버를 만들 예정!
     * Session 사용 안함
     * */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.debug("디버그 :filterChain 빈 등록됨");
        http.headers().frameOptions().disable(); // iframe 허용 안함.
        http.csrf().disable(); // enable이면 post맨이 작동 안함
        http.cors().configurationSource(configurationSource()); // XSS -> 얘는 일단 허용

        // JSessionId를 서버에서 관리 안하겠다.
        // STATELESS
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 폼 로그인 방식 안 쓸 거임 -> 리액트나, 앱으로 요청할 예정
        http.formLogin().disable();

        // 브라우저가 팝업창을 이용해서 사용자 인증을 진행하는 걸 막겠다.
        http.httpBasic().disable();

        // Exception 가로채기
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
//            response.setContentType("application/json; charset=utf-8");
            response.setStatus(403);
            response.getWriter().println("error");
        });

        http.authorizeRequests()
                .antMatchers("/api/s/**").authenticated()
                .antMatchers("/api/admin/**").hasRole(UserEnum.ADMIN.getValue()) // 최근에는 "ROLE_" 안 붙여도됨
                .anyRequest().permitAll();

        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        log.debug("디버그 :configurationSource cors 설정이 필터체인에 등록됨");

        // 설정 세팅
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (자바스크립트 요청 허용)
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용(프론트 엔드 IP만 허용)
        configuration.setAllowCredentials(true); // 클라에서 쿠키 요청 허용

        // 소스에 설정 주입
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
