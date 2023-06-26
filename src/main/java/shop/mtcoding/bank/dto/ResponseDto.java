package shop.mtcoding.bank.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public class ResponseDto<T> {

    private final Integer code; // 1 성공, -1 실패
    private final String msg;
    private final T data;
}
