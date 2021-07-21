package ie.lyit.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ie.lyit.app.config.Constants;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.BatchSize;

/**
 * A user.
 */
@Entity
@Table(name = "jhi_user")
public class User extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * generated id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * login to use
     */
    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    /**
     * users password
     */
    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    /**
     * users firstname
     */
    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    /**
     * users last name
     */
    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    /**
     * users email
     */
    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;

    /**
     * activated or not
     */
    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    /**
     * langKey to use
     */
    @Size(min = 2, max = 10)
    @Column(name = "lang_key", length = 10)
    private String langKey;

    /**
     * imageUrl to use
     */
    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    /**
     * activation key to use
     */
    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    /**
     * reset key to use
     */
    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    /**
     * resetDate
     */
    @Column(name = "reset_date")
    private Instant resetDate = null;

    /**
     * Authorities assigned to user
     */
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "jhi_user_authority",
        joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") }
    )
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();

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
    // Lowercase the login before saving it in database
    public void setLogin(String login) {
        this.login = StringUtils.lowerCase(login, Locale.ENGLISH);
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
    public String getActivationKey() {
        return activationKey;
    }

    /**
     *
     * @param activationKey -
     */
    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    /**
     *
     * @return -
     */
    public String getResetKey() {
        return resetKey;
    }

    /**
     *
     * @param resetKey -
     */
    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    /**
     *
     * @return -
     */
    public Instant getResetDate() {
        return resetDate;
    }

    /**
     *
     * @param resetDate -
     */
    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
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
    public Set<Authority> getAuthorities() {
        return authorities;
    }

    /**
     *
     * @param authorities -
     */
    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    /**
     *
     * @param o -
     * @return -
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return id != null && id.equals(((User) o).id);
    }

    /**
     *
     * @return -
     */
    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    /**
     *
     * @return -
     */
    // prettier-ignore
    @Override
    public String toString() {
        return "User{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated='" + activated + '\'' +
            ", langKey='" + langKey + '\'' +
            ", activationKey='" + activationKey + '\'' +
            "}";
    }
}
