package deepdive.backend.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    // TODO : SeviceException, DomainException 등으로 예외 처리 후 삭제
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleIllegalArgumentException(IllegalArgumentException ex) {
        return "잘못된 요청입니다: " + ex.getMessage();
    }
}
