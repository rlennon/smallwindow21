package ie.lyit.app.web.rest.errors;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 *
 */
public class BadRequestAlertException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;
    /**
     * Entityname string
     */
    private final String entityName;

    /**
     * errorKey string
     */
    private final String errorKey;

    /**
     * Constructor
     * @param defaultMessage -
     * @param entityName -
     * @param errorKey -
     */
    public BadRequestAlertException(String defaultMessage, String entityName, String errorKey) {
        this(ErrorConstants.DEFAULT_TYPE, defaultMessage, entityName, errorKey);
    }

    /**
     * Constructor
     * @param type -
     * @param defaultMessage -
     * @param entityName -
     * @param errorKey -
     */
    public BadRequestAlertException(URI type, String defaultMessage, String entityName, String errorKey) {
        super(type, defaultMessage, Status.BAD_REQUEST, null, null, null, getAlertParameters(entityName, errorKey));
        this.entityName = entityName;
        this.errorKey = errorKey;
    }

    /**
     *
     * @return -
     */
    public String getEntityName() {
        return entityName;
    }

    /**
     *
     * @return -
     */
    public String getErrorKey() {
        return errorKey;
    }

    private static Map<String, Object> getAlertParameters(String entityName, String errorKey) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("message", "error." + errorKey);
        parameters.put("params", entityName);
        return parameters;
    }
}
