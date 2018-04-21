package io.moonlight.utils.exception;

/**
 * Created by qt on 2017/8/15.
 */
public class NotFoundException extends InvokeException {


    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(int code, String message) {
        super(code, message);
    }

}
