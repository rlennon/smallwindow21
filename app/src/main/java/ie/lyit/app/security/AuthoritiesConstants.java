package ie.lyit.app.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    /**
     * Admin Constant
     */
    public static final String ADMIN = "ROLE_ADMIN";

    /**
     * User constant
     */
    public static final String USER = "ROLE_USER";

    /**
     * Anonymous constant
     */
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    /**
     * Private class constructor
     */
    private AuthoritiesConstants() {}
}
