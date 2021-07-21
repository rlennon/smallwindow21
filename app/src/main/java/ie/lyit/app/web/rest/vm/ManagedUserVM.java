package ie.lyit.app.web.rest.vm;

import ie.lyit.app.service.dto.AdminUserDTO;
import javax.validation.constraints.Size;

/**
 * View Model extending the AdminUserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserVM extends AdminUserDTO {

    /**
     * Password min length constant
     */
    public static final int PASSWORD_MIN_LENGTH = 4;

    /**
     * Password max length constant
     */
    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    /**
     * class constructor
     */
    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

    /**
     * getPassword
     * @return string password
     */
    public String getPassword() {
        return password;
    }

    /**
     * set password
     * @param password -
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * toString method
     */
    // prettier-ignore
    @Override
    public String toString() {
        return "ManagedUserVM{" + super.toString() + "} ";
    }
}
