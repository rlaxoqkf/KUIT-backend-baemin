package kuit.server.common.exception.jwt;

import kuit.server.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class JwtInvalidTokenException extends RuntimeException {

    private final ResponseStatus exceptionStatus;

    public JwtInvalidTokenException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }

}
