package io.moonlight.utils.exception;

/**
 * Created by qt on 2017/8/15.
 */
public class InvokeException extends RuntimeException {

    protected int code;

    public InvokeException() {}

    public InvokeException(String message) {
        super(message);
    }

    public InvokeException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
