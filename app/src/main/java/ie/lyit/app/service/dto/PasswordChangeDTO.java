package ie.lyit.app.service.dto;

/**
 * A DTO representing a password change required data - current and new password.
 */
public class PasswordChangeDTO {

    private String currentPassword;
    private String newPassword;

    /**
     * Constructor
     */
    public PasswordChangeDTO() {
        // Empty constructor needed for Jackson.
    }

    /**
     * Constructor
     * @param currentPassword -
     * @param newPassword -
     */
    public PasswordChangeDTO(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    /**
     *
     * @return -
     */
    public String getCurrentPassword() {
        return currentPassword;
    }

    /**
     *
     * @param currentPassword -
     */
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
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
