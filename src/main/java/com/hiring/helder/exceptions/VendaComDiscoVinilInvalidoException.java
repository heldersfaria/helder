package com.hiring.helder.exceptions;


public class VendaComDiscoVinilInvalidoException extends Exception {

    private static final long serialVersionUID = 904049274547762569L;

    public VendaComDiscoVinilInvalidoException() {
    }

    /**
     * @param message
     */
    public VendaComDiscoVinilInvalidoException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public VendaComDiscoVinilInvalidoException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public VendaComDiscoVinilInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public VendaComDiscoVinilInvalidoException(String message, Throwable cause, boolean enableSuppression,
                                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}