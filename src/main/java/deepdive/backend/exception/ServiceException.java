package deepdive.backend.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

    public final ExceptionStatus status;

    public ServiceException(ExceptionStatus status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return status.getMessage();
    }

}
