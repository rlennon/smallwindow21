package ie.lyit.app.config;

/**
 * Application constants.
 */
public final class Constants {

    /**
     * Regex for acceptable logins
     */
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    /**
     * system constant
     */
    public static final String SYSTEM = "system";
    /**
     * language constant
     */
    public static final String DEFAULT_LANGUAGE = "en";

    /**
     * Private class constructor
     */
    private Constants() {}
}
