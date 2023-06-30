package shop.mtcoding.bank.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import shop.mtcoding.bank.dto.ResponseDto;

import javax.servlet.http.HttpServletResponse;

public class CustomResponseUtil {

    private static final Logger log = LoggerFactory.getLogger(CustomResponseUtil.class);

    public static void fail(HttpServletResponse response, String msg, HttpStatus code) {
        try{
            ObjectMapper om = new ObjectMapper();
            ResponseDto<?> responseDto = new ResponseDto<>(-1, msg, null);
            String responseBody = om.writeValueAsString(responseDto);

            response.setContentType("application/json; charset=utf-8");
            response.setStatus(code.value());
            response.getWriter().println(responseBody);
        } catch (Exception e) {
            log.error("서버 파싱 에러");
        }
    }

    public static void success(HttpServletResponse response, Object dto, HttpStatus code) {
        try{
            ObjectMapper om = new ObjectMapper();
            ResponseDto<?> responseDto = new ResponseDto<>(1, "로그인 성공", dto);
            String responseBody = om.writeValueAsString(responseDto);

            response.setContentType("application/json; charset=utf-8");
            response.setStatus(code.value());
            response.getWriter().println(responseBody);
        } catch (Exception e) {
            log.error("서버 파싱 에러");
        }
    }


}
