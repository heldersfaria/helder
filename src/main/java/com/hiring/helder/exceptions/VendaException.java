package com.hiring.helder.exceptions;

public class VendaException extends Exception {

    private static final long serialVersionUID = 904049274547762569L;

    public VendaException() {
    }

    /**
     * @param message
     */
    public VendaException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public VendaException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public VendaException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public VendaException(String message, Throwable cause, boolean enableSuppression,
                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
