package ie.lyit.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * The Employee entity.
 */
@ApiModel(description = "The Employee entity.")
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * generated id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The firstname attribute.
     */
    @ApiModelProperty(value = "The firstname attribute.")
    @Column(name = "first_name")
    private String firstName;

    /**
     * last name assigned
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * employees email
     */
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * s3 image key for profile image
     */
    @Column(name = "s_3_image_key")
    private String s3ImageKey;

    /**
     * files associated to user
     */
    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties(value = { "employee" }, allowSetters = true)
    private Set<File> files = new HashSet<>();

    /**
     * skills associated to user
     */
    @ManyToMany(mappedBy = "employees")
    @JsonIgnoreProperties(value = { "categories", "employees" }, allowSetters = true)
    private Set<Skill> skills = new HashSet<>();

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
     * @param id -
     * @return -
     */
    public Employee id(Long id) {
        this.id = id;
        return this;
    }

    /**
     *
     * @return -
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     *
     * @param firstName -
     * @return -
     */
    public Employee firstName(String firstName) {
        this.firstName = firstName;
        return this;
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
        return this.lastName;
    }

    /**
     *
     * @param lastName -
     * @return -
     */
    public Employee lastName(String lastName) {
        this.lastName = lastName;
        return this;
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
        return this.email;
    }

    /**
     *
     * @param email -
     * @return -
     */
    public Employee email(String email) {
        this.email = email;
        return this;
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
    public String gets3ImageKey() {
        return this.s3ImageKey;
    }

    /**
     *
     * @param s3ImageKey -
     * @return -
     */
    public Employee s3ImageKey(String s3ImageKey) {
        this.s3ImageKey = s3ImageKey;
        return this;
    }

    /**
     *
     * @param s3ImageKey -
     */
    public void sets3ImageKey(String s3ImageKey) {
        this.s3ImageKey = s3ImageKey;
    }

    /**
     *
     * @return -
     */
    public Set<File> getFiles() {
        return this.files;
    }

    /**
     *
     * @param files -
     * @return -
     */
    public Employee files(Set<File> files) {
        this.setFiles(files);
        return this;
    }

    /**
     *
     * @param file -
     * @return -
     */
    public Employee addFile(File file) {
        this.files.add(file);
        file.setEmployee(this);
        return this;
    }

    /**
     *
     * @param file -
     * @return -
     */
    public Employee removeFile(File file) {
        this.files.remove(file);
        file.setEmployee(null);
        return this;
    }

    /**
     *
     * @param files -
     */
    public void setFiles(Set<File> files) {
        if (this.files != null) {
            this.files.forEach(i -> i.setEmployee(null));
        }
        if (files != null) {
            files.forEach(i -> i.setEmployee(this));
        }
        this.files = files;
    }

    /**
     *
     * @return -
     */
    public Set<Skill> getSkills() {
        return this.skills;
    }

    /**
     *
     * @param skills -
     * @return -
     */
    public Employee skills(Set<Skill> skills) {
        this.setSkills(skills);
        return this;
    }

    /**
     *
     * @param skill -
     * @return -
     */
    public Employee addSkill(Skill skill) {
        this.skills.add(skill);
        skill.getEmployees().add(this);
        return this;
    }

    /**
     *
     * @param skill -
     * @return -
     */
    public Employee removeSkill(Skill skill) {
        this.skills.remove(skill);
        skill.getEmployees().remove(this);
        return this;
    }

    /**
     *
     * @param skills -
     */
    public void setSkills(Set<Skill> skills) {
        if (this.skills != null) {
            this.skills.forEach(i -> i.removeEmployee(this));
        }
        if (skills != null) {
            skills.forEach(i -> i.addEmployee(this));
        }
        this.skills = skills;
    }

    /**
     *
     * @param o -
     * @return -
     */
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
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

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", s3ImageKey='" + gets3ImageKey() + "'" +
            "}";
    }
}
