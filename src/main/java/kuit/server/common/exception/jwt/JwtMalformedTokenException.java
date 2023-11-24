package kuit.server.common.exception.jwt;

import kuit.server.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class JwtMalformedTokenException extends RuntimeException {

    private final ResponseStatus exceptionStatus;

    public JwtMalformedTokenException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }

}
