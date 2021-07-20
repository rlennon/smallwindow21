package ie.lyit.app.web.rest.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * View Model object for storing a user's credentials.
 */
public class LoginVM {

    @NotNull
    @Size(min = 1, max = 50)
    private String username;

    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    private boolean rememberMe;

    /**
     *
     * @return -
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username -
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return -
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password -
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return -
     */
    public boolean isRememberMe() {
        return rememberMe;
    }

    /**
     *
     * @param rememberMe -
     */
    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    /**
     *
     * @return -
     */
    // prettier-ignore
    @Override
    public String toString() {
        return "LoginVM{" +
            "username='" + username + '\'' +
            ", rememberMe=" + rememberMe +
            '}';
    }
}
