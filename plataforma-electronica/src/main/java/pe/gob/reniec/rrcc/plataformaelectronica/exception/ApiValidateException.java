package pe.gob.reniec.rrcc.plataformaelectronica.exception;

public class ApiValidateException extends RuntimeException {
    public ApiValidateException() {
        super();
    }

    public ApiValidateException(String message) {
        super(message);
    }

    public ApiValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiValidateException(Throwable cause) {
        super(cause);
    }

    protected ApiValidateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
