package io.moonlight.utils.exception;

/**
 * Created by qt on 2017/8/15.
 */
public class NullException extends InvokeException{


    public NullException() {
    }

    public NullException(int code, String message) {
        super(code, message);
    }

    public NullException(String message) {
        super(message);
    }
}
