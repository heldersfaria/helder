package com.hiring.helder.exceptions;


public class DiscoException extends Exception {

    private static final long serialVersionUID = 904049274547762569L;

    public DiscoException() {
    }

    /**
     * @param message
     */
    public DiscoException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public DiscoException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public DiscoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public DiscoException(String message, Throwable cause, boolean enableSuppression,
                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}