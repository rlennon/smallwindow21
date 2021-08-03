package ie.lyit.app.web.rest.vm;

/**
 * View Model object for storing the user's key and password.
 */
public class KeyAndPasswordVM {

    private String key;

    private String newPassword;

    /**
     *
     * @return -
     */
    public String getKey() {
        return key;
    }

    /**
     *
     * @param key -
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     *
     * @return -
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     *
     * @param newPassword -
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
