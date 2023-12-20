package pe.gob.reniec.rrcc.plataformaelectronica.exception;

public class ApiErrorException  extends RuntimeException {
    public ApiErrorException() {
        super();
    }

    public ApiErrorException(String message) {
        super(message);
    }

    public ApiErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiErrorException(Throwable cause) {
        super(cause);
    }

    protected ApiErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
