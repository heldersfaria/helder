package com.hiring.helder.exceptions;


public class DiscoVinilNaoEncontradoException extends Exception {

    private static final long serialVersionUID = 904049274547762569L;

    public DiscoVinilNaoEncontradoException() {
    }

    /**
     * @param message
     */
    public DiscoVinilNaoEncontradoException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public DiscoVinilNaoEncontradoException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public DiscoVinilNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public DiscoVinilNaoEncontradoException(String message, Throwable cause, boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}