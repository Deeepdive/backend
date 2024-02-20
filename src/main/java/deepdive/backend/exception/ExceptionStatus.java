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
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    NOT_FOUND_USER_BY_EMAIL(HttpStatus.NOT_FOUND, "이메일과 일치하는 유저가 존재하지 않습니다."),
    NOT_FOUND_LOG(HttpStatus.NOT_FOUND, "존재하지 않는 로그입니다."),
    DUPLICATE_REGISTER(HttpStatus.BAD_REQUEST, "가입한 내역이 존재합니다. 다른 email로 시도해주세요."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "해당 닉네임은 이미 사용중입니다."),
    DUPLICATE_GOOGLE(HttpStatus.CONFLICT, "google sns로 가입한 이력이 있습니다."),
    DUPLICATE_NAVER(HttpStatus.CONFLICT, "naver sns로 가입한 이력이 있습니다."),
    DUPLICATE_KAKAO(HttpStatus.CONFLICT, "kakao sns로 가입한 이력이 있습니다."),
    INVALID_ARGUMENT(HttpStatus.BAD_REQUEST, "유효하지 않은 입력입니다"),
    INVALID_USER_ID(HttpStatus.BAD_REQUEST, "허용되지 않은 ID 값입니다."),
    INVALID_PROVIDER(HttpStatus.BAD_REQUEST, "허용되지 않은 provider 입니다."),
    INVALID_REGISTER(HttpStatus.BAD_REQUEST, "SNS 정보와 회원 정보가 일치하지 않습니다."),
    INVALID_ORGANIZATION_TYPE(HttpStatus.BAD_REQUEST, "알 수 없는 기관입니다."),
    INVALID_CERT_TYPE(HttpStatus.BAD_REQUEST, "알 수 없는 자격증 명입니다."),
    INVALID_MATCH_PROFILE(HttpStatus.BAD_REQUEST, "발급 기관과 자격증 유형이 일치하지 않습니다."),
    INVALID_NUMBER_TYPE(HttpStatus.BAD_REQUEST, "번호와 일치하는 url이 존재하지 않습니다."),
    DUPLICATE_APPLE(HttpStatus.CONFLICT, "apple sns로 가입한 이력이 있습니다."),
    INVALID_DIVE_DATE(HttpStatus.BAD_REQUEST, "현재 날짜(한국 기준)보다 이틀 이후의 date는 입력할 수 없습니다."),
    NOT_FOUND_PROFILE(HttpStatus.NOT_FOUND, "프로필이 존재하지 않습니다."),
    INVALID_NICKNAME(HttpStatus.NOT_FOUND, "닉네임은 영문, 숫자, 길이는 20자 이하여야합니다."),
    INVALID_OS(HttpStatus.NOT_FOUND, "IOS, ANDROID 형식이 올바르지 않습니다."),
    INVALID_BUDDY_PROFILE(HttpStatus.BAD_REQUEST, "스스로 buddy로 추가할 수 없습니다"),
    INVALID_ENUM_TYPE(HttpStatus.BAD_REQUEST, "Enum 타입이 일치하지 않습니다.");

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
