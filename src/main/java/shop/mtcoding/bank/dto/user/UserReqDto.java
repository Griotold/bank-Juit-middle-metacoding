package shop.mtcoding.bank.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserReqDto {
    @Getter
    @Setter
    public static class JoinReqDto{
        // 영문, 숫자는 되고, 2~20자 이내
        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "영문, 숫자 2~20자 이내로 작성해주세요.")
        @NotEmpty
        private String username;

        // 영문, 숫자, 특수기호가 되고, 4~20자
        @NotEmpty
        @Size(min = 4, max = 20, message = "4 ~ 20자 이내로 작성 부탁드립니다.")
        private String password;

        // 이메일 형식
        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z0-9]{2,3}$",
                message = "이메일 형식으로 작성해주세요.")
        private String email;

        // 영어, 한글, 1~20자
        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z가-힣]{1,20}$", message = "한글, 영문 1~20자 이내로 작성해주세요.")
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
