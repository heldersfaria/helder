package com.hiring.helder.exceptions;


public class DiscoVinilException extends Exception {

    private static final long serialVersionUID = 904049274547762569L;

    public DiscoVinilException() {
    }

    /**
     * @param message
     */
    public DiscoVinilException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public DiscoVinilException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public DiscoVinilException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public DiscoVinilException(String message, Throwable cause, boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}