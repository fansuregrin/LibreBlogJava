package org.fansuregrin.exception;

public class RequestDataException extends RuntimeException {
    public RequestDataException(String message) {
        super(message);
    }

    public RequestDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
