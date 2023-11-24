package kuit.server.common.exception.jwt;

import kuit.server.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class JwtUnsupportedTokenException extends RuntimeException {

    private final ResponseStatus exceptionStatus;

    public JwtUnsupportedTokenException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }

}
