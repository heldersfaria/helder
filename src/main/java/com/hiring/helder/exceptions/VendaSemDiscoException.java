package com.hiring.helder.exceptions;

public class VendaSemDiscoException extends Exception {

    private static final long serialVersionUID = 904049274547762569L;

    public VendaSemDiscoException() {
    }

    /**
     * @param message
     */
    public VendaSemDiscoException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public VendaSemDiscoException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public VendaSemDiscoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public VendaSemDiscoException(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
