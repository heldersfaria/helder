package com.hiring.helder.exceptions;


public class HelderApplicationException extends Exception {

    private static final long serialVersionUID = 904049274547762569L;

    public HelderApplicationException() {
    }

    /**
     * @param message
     */
    public HelderApplicationException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public HelderApplicationException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public HelderApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public HelderApplicationException(String message, Throwable cause, boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}