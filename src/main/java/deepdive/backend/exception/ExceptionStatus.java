package deepdive.backend.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@JsonFormat(shape = Shape.OBJECT)
@RequiredArgsConstructor
@Getter
public enum ExceptionStatus {
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    NOT_FOUND_USER_BY_EMAIL(HttpStatus.NOT_FOUND, "이메일과 일치하는 유저가 존재하지 않습니다."),
    NOT_FOUND_LOG(HttpStatus.NOT_FOUND, "존재하지 않는 로그입니다."),
    DUPLICATE_REGISTER(HttpStatus.BAD_REQUEST, "가입한 내역이 존재합니다. 다른 email로 시도해주세요."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "해당 닉네임은 이미 사용중입니다."),
    INVALID_ARGUMENT(HttpStatus.BAD_REQUEST, "유효하지 않은 입력입니다"),
    INVALID_USER_ID(HttpStatus.BAD_REQUEST, "허용되지 않은 ID 값입니다.");

    private final int errorCode;
    private final String message;
    private final String error;

    ExceptionStatus(HttpStatus status, String message) {
        this.errorCode = status.value();
        this.message = message;
        this.error = status.getReasonPhrase();
    }

    public ServiceException asServiceException() {
        return new ServiceException(this);
    }

    public DomainException asDomainException() {
        return new DomainException(this);
    }
}
