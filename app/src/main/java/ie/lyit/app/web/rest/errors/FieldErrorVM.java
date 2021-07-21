package ie.lyit.app.web.rest.errors;

import java.io.Serializable;

/**
 *
 */
public class FieldErrorVM implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * objectName string
     */
    private final String objectName;
    /**
     * field string
     */
    private final String field;
    /**
     * messagestring
     */
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
