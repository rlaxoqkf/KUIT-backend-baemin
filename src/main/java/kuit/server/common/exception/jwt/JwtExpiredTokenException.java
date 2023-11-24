package kuit.server.common.exception.jwt;

import kuit.server.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class JwtExpiredTokenException extends RuntimeException {

    private final ResponseStatus exceptionStatus;

    public JwtExpiredTokenException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }

}
