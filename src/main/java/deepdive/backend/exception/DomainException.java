package deepdive.backend.exception;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {

    public final ExceptionStatus status;

    public DomainException(ExceptionStatus status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return status.getMessage();
    }

}
