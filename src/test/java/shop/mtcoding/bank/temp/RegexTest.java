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

    @Test
    @DisplayName("구분 테스트 1")
    void account_gubun_test1() {
        // given
        String gubun = "DEPOSIT";

        // when
        boolean result = Pattern.matches("^(DEPOSIT)$", gubun);
        System.out.println("result = " + result);
        // then
    }

    @Test
    @DisplayName("구분 테스트 2")
    void account_gubun_test2() {
        // given
        String gubun = "DEPOSIT";
        String gubun2 = "TRANSFER";

        // when
        boolean result = Pattern.matches("^(DEPOSIT|TRANSFER)$", gubun);
        boolean result2 = Pattern.matches("^(DEPOSIT|TRANSFER)$", gubun2);
        System.out.println("result = " + result);
        System.out.println("result2 = " + result2);

        // then
    }
    @Test
    @DisplayName("전화번호 테스트 1")
    void account_tel_test() {
        // given
        String tel = "010-3333-7777";

        // when
        boolean result = Pattern.matches("^[0-9]{3}-[0-9]{4}-[0-9]{4}", tel);
        System.out.println("result = " + result);

        // then
    }
    @Test
    @DisplayName("전화번호 테스트 2")
    void account_tel_test2() {
        // given
        String tel = "01033337777";

        // when
        boolean result = Pattern.matches("^[0-9]{11}", tel);
        System.out.println("result = " + result);

        // then
    }
}
