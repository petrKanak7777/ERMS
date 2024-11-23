package cz.erms.backend.error.exception;

import java.io.Serial;

public class InvalidArgumentException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -7126515001854351428L;

    public InvalidArgumentException() {
        super();
    }

    public InvalidArgumentException(String s) {
        super(s);
    }

    public InvalidArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidArgumentException(Throwable cause) {
        super(cause);
    }
}
