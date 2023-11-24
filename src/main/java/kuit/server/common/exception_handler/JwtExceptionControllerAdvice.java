package kuit.server.common.exception_handler;

import kuit.server.common.exception.jwt.*;
import kuit.server.common.response.BaseErrorResponse;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.Priority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Priority(0)
@RestControllerAdvice
public class JwtExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({JwtNoTokenException.class, JwtUnsupportedTokenException.class})
    public BaseErrorResponse handle_JwtBadRequestException(JwtNoTokenException e) {
        log.error("[handle_JwtBadRequestException]", e);
        return new BaseErrorResponse(e.getExceptionStatus());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({JwtExpiredTokenException.class, JwtInvalidTokenException.class, JwtMalformedTokenException.class, JwtUnauthorizedTokenException.class, JwtException.class})
    public BaseErrorResponse handle_JwtUnauthorizedException(JwtUnauthorizedTokenException e) {
        log.error("[handle_JwtUnauthorizedException]", e);
        return new BaseErrorResponse(e.getExceptionStatus());
    }

}
