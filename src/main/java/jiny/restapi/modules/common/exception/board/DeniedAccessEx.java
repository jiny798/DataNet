package jiny.restapi.modules.common.exception.board;

public class DeniedAccessEx extends RuntimeException{
    public DeniedAccessEx() {
        super();
    }

    public DeniedAccessEx(String message) {
        super(message);
    }

    public DeniedAccessEx(String message, Throwable cause) {
        super(message, cause);
    }

    public DeniedAccessEx(Throwable cause) {
        super(cause);
    }

    protected DeniedAccessEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
