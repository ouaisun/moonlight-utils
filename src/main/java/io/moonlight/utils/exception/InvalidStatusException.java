package io.moonlight.utils.exception;

/**
 * Created by qt on 2017/8/15.
 */
public class InvalidStatusException extends InvokeException {


    public InvalidStatusException() {
    }

    public InvalidStatusException(int code, String message) {
        super(code, message);
    }

    public InvalidStatusException(String message) {
        super(message);
    }
}
