package shop.mtcoding.bank.temp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LongTest {

    @Test
    @DisplayName("long 타입 단순 비교")
    void longSimple_test() {
        // given
        Long number1 = 1111L;
        Long number2 = 1111L;

        // when
        if (number1 == number2) {
            System.out.println("테스트 = 동일합니다.");
        } else {
            System.out.println("테스트 = 동일하지 않습니다.");
        }
    }
    @Test
    @DisplayName("long 타입 longValue() 비교")
    void longValue_test() {
        // given
        Long number1 = 1111L;
        Long number2 = 1111L;

        // when
        if (number1.longValue() == number2.longValue()) {
            System.out.println("테스트 = 동일합니다.");
        } else {
            System.out.println("테스트 = 동일하지 않습니다.");
        }
    }

    @Test
    @DisplayName("2의 8제곱까지는 단순 비교 가능")
    void smallValue_test() {
        // given
        Long num1 = 127L;
        Long num2 = 127L;

        if (num1 == num2) {
            System.out.println("같습니다");
        } else {
            System.out.println("다릅니다.");
        }
    }
    @Test
    @DisplayName("assertThat().isEqualTo()는 큰값도 비교 가능")
    void assertThat_isEqualTo_test() {
        // given
        Long num1 = 1270L;
        Long num2 = 1270L;

        // then
        Assertions.assertThat(num1).isEqualTo(num2);
    }
}
