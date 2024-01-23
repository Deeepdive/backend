package deepdive.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> serviceExceptionHandler(ServiceException e) {
        return ResponseEntity
            .status(e.status.getErrorCode())
            .body(e.status);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<?> domainExceptionHandler(ServiceException e) {
        return ResponseEntity
            .status(e.status.getErrorCode())
            .body(e.status);
    }
}
