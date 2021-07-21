package ie.lyit.app.security;

import org.springframework.security.core.AuthenticationException;

/**
 * This exception is thrown in case of a not activated user trying to authenticate.
 */
public class UserNotActivatedException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    /**
     *
     * @param message -
     */
    public UserNotActivatedException(String message) {
        super(message);
    }

    /**
     *
     * @param message -
     * @param t -
     */
    public UserNotActivatedException(String message, Throwable t) {
        super(message, t);
    }
}
