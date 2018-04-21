package io.moonlight.utils.exception;

/**
 * Created by qt on 2017/8/15.
 */
public class InvalidResultException extends InvokeException {


    public InvalidResultException() {
    }

    public InvalidResultException(int code, String message) {
        super(code, message);
    }

    public InvalidResultException(String message) {
        super(message);
    }
}
