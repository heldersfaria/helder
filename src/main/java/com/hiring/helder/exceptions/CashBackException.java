package com.hiring.helder.exceptions;

public class CashBackException extends Exception {

    private static final long serialVersionUID = 904049274547762569L;

    public CashBackException() {
    }

    /**
     * @param message
     */
    public CashBackException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public CashBackException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public CashBackException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public CashBackException(String message, Throwable cause, boolean enableSuppression,
                             boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
