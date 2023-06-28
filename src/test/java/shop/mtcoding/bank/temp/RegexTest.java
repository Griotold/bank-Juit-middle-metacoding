package shop.mtcoding.bank.temp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.*;

// java.util.regex.Pattern
public class RegexTest {

    @Test
    @DisplayName("한글만 된다")
    void onlyKorean() {
        // given
        String value = "한글만되도록";

        // when
        boolean result = Pattern.matches("^[가-힣]+$", value);

        // then
        System.out.println("result = " + result);
        assertThat(result).isTrue();
    }
    @Test
    @DisplayName("한글은 안된다")
    void noKorean() {
        // given
        String value = "ㅑ";

        // when
        boolean result = Pattern.matches("^[^ㄱ-ㅎㅏ-ㅣ가-힣]+$", value);

        // then
        System.out.println("result = " + result);
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("영어만 된다.")
    void onlyEng() {
        // given
        String value = "onlyEnglish";

        // when
        boolean result = Pattern.matches("^[a-zA-Z]+$", value);

        // then
        System.out.println("result = " + result);
        assertThat(result).isTrue();
    }
    @Test
    @DisplayName("영어는 안된다.")
    void noEng() {
        // given
        String value = "noEng";

        // when
        boolean result = Pattern.matches("^[^a-zA-Z]+$", value);

        // then
        System.out.println("result = " + result);
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("영어와 숫자만 된다.")
    void engPlusNum() {
        // given
        String value = "engli1234";

        // when
        boolean result = Pattern.matches("^[a-zA-Z0-9]+$", value);

        // then
        System.out.println("result = " + result);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("영어만되고 길이는 2~4")
    void onlyEng2To4() {
        // given
        String value = "only";

        // when
        boolean result = Pattern.matches("^[a-zA-Z]{2,4}$", value);

        // then
        System.out.println("result = " + result);
        assertThat(result).isTrue();
    }
    /**
     * username 제약 조건 : 영문, 숫자는 되고, 2~20자 이내
     */
    @Test
    @DisplayName("username 제약 조건 해보기")
    void usernameValidation() {
        // given
        String value = "ssar1234";

        // when
        boolean result = Pattern.matches("^[a-zA-Z0-9]{2,20}$", value);

        // then
        System.out.println("result = " + result);
        assertThat(result).isTrue();
    }
    /**
     * password 제약 조건 : 영문, 숫자, 특수기호가 되고, 4~20자
     */
    @Test
    @DisplayName("password 제약 조건 해보기")
    void passwordValidation() {
        // given
        String value = "cos4567!!";

        // when
        boolean result = Pattern.matches("^[^ㄱ-ㅎㅏ-ㅣ가-힣]{4,20}$", value);

        // then
        System.out.println("result = " + result);
        assertThat(result).isTrue();
    }
    /**
     * fullname 제약 조건 : 영문, 한글 1~20자
     */
    @Test
    @DisplayName("fullname 제약 조건 해보기")
    void user_fullname_Validation() {
        // given
        String value = "쌀쌀";

        // when
        boolean result = Pattern.matches("^[a-zA-Z가-힣]{1,20}$", value);

        // then
        System.out.println("result = " + result);
        assertThat(result).isTrue();
    }

    /**
     * email 제약 조건 :
     */
    @Test
    @DisplayName("email 제약 조건 해보기")
    void user_email_Validation() {
        // given
        String value = "ssar@nate.com";

        // when
        boolean result = Pattern.matches("^[a-zA-Z0-9]{2,6}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z0-9]{2,3}$", value);

        // then
        System.out.println("result = " + result);
        assertThat(result).isTrue();
    }
}
