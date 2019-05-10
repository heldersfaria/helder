package com.hiring.helder.exceptions;

public class SpotifyApiException extends Exception {

    private static final long serialVersionUID = 904049274547762569L;

    public SpotifyApiException() {
    }

    /**
     * @param message
     */
    public SpotifyApiException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public SpotifyApiException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public SpotifyApiException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SpotifyApiException(String message, Throwable cause, boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
