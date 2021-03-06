package de.hdm_stuttgart.huber.itprojekt.server;

/**
 * Wird geworfen, wenn versucht wird, Methoden, die nur nach Login zulässig sind,
 * ohne serverseitig angemeldeten Nutzer zu verwenden. Ähnelt ansonsten <code>RuntimeException</code>
 *
 * @see RuntimeException
 * @author Dominik Huber
 */
public class InvalidLoginStatusException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -6623416840322170431L;

    public InvalidLoginStatusException() {
        // TODO Auto-generated constructor stub
    }

    public InvalidLoginStatusException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public InvalidLoginStatusException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    public InvalidLoginStatusException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public InvalidLoginStatusException(String message, Throwable cause, boolean enableSuppression,
                                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

}
