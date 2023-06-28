package shop.mtcoding.bank.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;

import javax.validation.constraints.NotEmpty;

public class UserReqDto {
    @Getter
    @Setter
    public static class JoinReqDto{
        // 영문, 숫자는 되고, 2~20자 이내
        @NotEmpty
        private String username;

        // 영문, 숫자, 특수기호가 되고, 4~20자
        @NotEmpty
        private String password;

        // 이메일 형식
        @NotEmpty
        private String email;

        // 영어, 한글, 1~2-자
        @NotEmpty
        private String fullname;

        public User toEntity(BCryptPasswordEncoder passwordEncoder) {
            return User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .email(email)
                    .fullname(fullname)
                    .role(UserEnum.CUSTOMER)
                    .build();
        }
    }



}
