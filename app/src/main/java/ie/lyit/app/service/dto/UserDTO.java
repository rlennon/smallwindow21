package ie.lyit.app.service.dto;

import ie.lyit.app.domain.User;

/**
 * A DTO representing a user, with only the public attributes.
 */
public class UserDTO {

    private Long id;

    private String login;

    /**
     * Constructor
     */
    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    /**
     * Constructor
     * @param user -
     */
    public UserDTO(User user) {
        this.id = user.getId();
        // Customize it here if you need, or not, firstName/lastName/etc
        this.login = user.getLogin();
    }

    /**
     *
     * @return -
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id -
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * getLogin
     * @return string login
     */
    public String getLogin() {
        return login;
    }

    /**
     *
     * @param login -
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return string
     */
    // prettier-ignore
    @Override
    public String toString() {
        return "UserDTO{" +
            "id='" + id + '\'' +
            ", login='" + login + '\'' +
            "}";
    }
}
