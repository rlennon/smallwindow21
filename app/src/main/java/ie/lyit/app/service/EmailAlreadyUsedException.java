package ie.lyit.app.service;

/**
 *
 */
public class EmailAlreadyUsedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public EmailAlreadyUsedException() {
        super("Email is already in use!");
    }
}
