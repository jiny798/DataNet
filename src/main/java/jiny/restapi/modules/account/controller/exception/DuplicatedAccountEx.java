package jiny.restapi.modules.account.controller.exception;

public class DuplicatedAccountEx extends RuntimeException{

    public DuplicatedAccountEx() {
        super();
    }
    public DuplicatedAccountEx(String message) {
        super(message);
    }
    public DuplicatedAccountEx(String message, Throwable cause) {
        super(message, cause);
    }
    public DuplicatedAccountEx(Throwable cause) {
        super(cause);
    }
    protected DuplicatedAccountEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
