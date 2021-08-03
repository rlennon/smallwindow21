package ie.lyit.app.service.dto;

import ie.lyit.app.config.Constants;
import ie.lyit.app.domain.Authority;
import ie.lyit.app.domain.User;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.*;

/**
 * A DTO representing a user, with his authorities.
 */
public class AdminUserDTO {

    private Long id;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @Size(max = 256)
    private String imageUrl;

    private boolean activated = false;

    @Size(min = 2, max = 10)
    private String langKey;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Set<String> authorities;

    /**
     *
     */
    public AdminUserDTO() {
        // Empty constructor needed for Jackson.
    }

    /**
     *
     * @param user -
     */
    public AdminUserDTO(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.activated = user.isActivated();
        this.imageUrl = user.getImageUrl();
        this.langKey = user.getLangKey();
        this.createdBy = user.getCreatedBy();
        this.createdDate = user.getCreatedDate();
        this.lastModifiedBy = user.getLastModifiedBy();
        this.lastModifiedDate = user.getLastModifiedDate();
        this.authorities = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet());
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
     *
     * @return -
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
     *
     * @return -
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName -
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return -
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName -
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return -
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email -
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return -
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     *
     * @param imageUrl -
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     *
     * @return -
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     *
     * @param activated -
     */
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    /**
     *
     * @return -
     */
    public String getLangKey() {
        return langKey;
    }

    /**
     *
     * @param langKey -
     */
    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    /**
     *
     * @return -
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @param createdBy -
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     *
     * @return -
     */
    public Instant getCreatedDate() {
        return createdDate;
    }

    /**
     *
     * @param createdDate -
     */
    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    /**
     *
     * @return -
     */
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     *
     * @param lastModifiedBy -
     */
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    /**
     *
     * @return -
     */
    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     *
     * @param lastModifiedDate -
     */
    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    /**
     *
     * @return -
     */
    public Set<String> getAuthorities() {
        return authorities;
    }

    /**
     *
     * @param authorities -
     */
    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    /**
     *
     * @return -
     */
    // prettier-ignore
    @Override
    public String toString() {
        return "AdminUserDTO{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", createdBy=" + createdBy +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", authorities=" + authorities +
            "}";
    }
}
