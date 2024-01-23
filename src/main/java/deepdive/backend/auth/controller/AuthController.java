package deepdive.backend.auth.controller;

import deepdive.backend.utils.response.Response;
import deepdive.backend.utils.response.ResponseMsg;
import deepdive.backend.utils.response.StatusCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @GetMapping("/loginBaaam~")
    public ResponseEntity<Response> login(HttpServletRequest request, HttpServletResponse response,
        @CookieValue(value = "ID", required = false) String key) {
        return new ResponseEntity<>(Response.of(StatusCode.OK, ResponseMsg.UNREGISTERED),
            HttpStatus.OK);
    }

    @PostMapping("/token")
    public ResponseEntity<Response> makeToken(HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody Map<String, String> code) {
        return new ResponseEntity<>(Response.of(StatusCode.OK, ResponseMsg.LOGIN_SUCCESS),
            HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<Response> logout(HttpServletRequest request,
        HttpServletResponse response) {
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>(Response.of(StatusCode.OK, ResponseMsg.LOGOUT_SUCCESS),
            HttpStatus.OK);
    }

}
