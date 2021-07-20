package ie.lyit.app.web.rest.errors;

import java.io.Serializable;

/**
 *
 */
public class FieldErrorVM implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String objectName;

    private final String field;

    private final String message;

    /**
     * Constructor
     * @param dto -
     * @param field -
     * @param message -
     */
    public FieldErrorVM(String dto, String field, String message) {
        this.objectName = dto;
        this.field = field;
        this.message = message;
    }

    /**
     *
     * @return -
     */
    public String getObjectName() {
        return objectName;
    }

    /**
     *
     * @return -
     */
    public String getField() {
        return field;
    }

    /**
     *
     * @return -
     */
    public String getMessage() {
        return message;
    }
}
