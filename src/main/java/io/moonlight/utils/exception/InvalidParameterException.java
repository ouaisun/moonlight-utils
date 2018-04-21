package io.moonlight.utils.exception;

/**
 * Created by qt on 2017/8/15.
 */
public class InvalidParameterException extends InvokeException {

    public InvalidParameterException() {
    }

    public InvalidParameterException(int code, String message) {
        super(code, message);
    }

    public InvalidParameterException(String message) {
        super(message);
    }
}
