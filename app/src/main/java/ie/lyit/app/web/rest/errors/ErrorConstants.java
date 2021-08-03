package ie.lyit.app.web.rest.errors;

import java.net.URI;

/**
 *
 */
public final class ErrorConstants {

    /**
     * Concurrency failure
     */
    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    /**
     * Validation error
     */
    public static final String ERR_VALIDATION = "error.validation";
    /**
     * Base url
     */
    public static final String PROBLEM_BASE_URL = "https://www.jhipster.tech/problem";
    /**
     * Default type
     */
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    /**
     * Constraint violation type
     */
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");
    /**
     * Invalid password
     */
    public static final URI INVALID_PASSWORD_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-password");
    /**
     * Email already in use
     */
    public static final URI EMAIL_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/email-already-used");
    /**
     * Login already in use
     */
    public static final URI LOGIN_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/login-already-used");

    private ErrorConstants() {}
}
