package deepdive.backend.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import deepdive.backend.slack.SlackNotification;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	public ResponseEntity<?> domainExceptionHandler(DomainException e) {
		return ResponseEntity
			.status(e.status.getErrorCode())
			.body(e.status);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> validExceptionHandler(MethodArgumentNotValidException e) {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(e.getDetailMessageArguments());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> httpMessageNotReadableHandler(HttpMessageNotReadableException e) {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body("JSON 형식이 올바르지 않습니다.");
	}

	@SlackNotification
	@ResponseStatus(INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> unexpectedExceptionHandler(HttpServletRequest request, Exception e) {
		return ResponseEntity
			.status(INTERNAL_SERVER_ERROR)
			.body(e);
	}
}
