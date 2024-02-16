package deepdive.backend.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.http.HttpStatus;

@Constraint(validatedBy = {EnumValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnum {

    Class<? extends java.lang.Enum<?>> enumClass();

    boolean ignoreCase() default false;

    String message() default "일치하는 ENUM값이 존재하지 않습니다.";

    HttpStatus status() default HttpStatus.BAD_REQUEST;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
