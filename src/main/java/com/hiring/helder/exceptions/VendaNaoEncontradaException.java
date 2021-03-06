package com.hiring.helder.exceptions;

public class VendaNaoEncontradaException extends Exception {

    private static final long serialVersionUID = 904049274547762569L;

    public VendaNaoEncontradaException() {
    }

    /**
     * @param message
     */
    public VendaNaoEncontradaException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public VendaNaoEncontradaException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public VendaNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public VendaNaoEncontradaException(String message, Throwable cause, boolean enableSuppression,
                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
