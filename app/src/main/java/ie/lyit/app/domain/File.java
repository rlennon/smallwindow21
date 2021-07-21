package ie.lyit.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A File.
 */
@Entity
@Table(name = "file")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * generated id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * s3e file key
     */
    @Column(name = "s_3_file_key")
    private String s3FileKey;

    /**
     * s3 file type
     */
    @Column(name = "s_3_file_type")
    private String s3FileType;

    /**
     * employee who owns file
     */
    @ManyToOne
    @JsonIgnoreProperties(value = { "files", "skills" }, allowSetters = true)
    private Employee employee;

    /**
     *
     * @return -
     */
    // jhipster-needle-entity-add-field - JHipster will add fields here
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
    public File id(Long id) {
        this.id = id;
        return this;
    }

    /**
     *
     * @return -
     */
    public String gets3FileKey() {
        return this.s3FileKey;
    }

    /**
     *
     * @return -
     */
    public String gets3FileType() {
        return this.s3FileType;
    }

    /**
     *
     * @param s3FileKey -
     * @return -
     */
    public File s3FileKey(String s3FileKey) {
        this.s3FileKey = s3FileKey;
        return this;
    }

    /**
     *
     * @param s3FileType -
     * @return -
     */
    public File s3FileType(String s3FileType) {
        this.s3FileType = s3FileType;
        return this;
    }

    /**
     *
     * @param s3FileKey -
     */
    public void sets3FileKey(String s3FileKey) {
        this.s3FileKey = s3FileKey;
    }

    /**
     *
     * @param s3FileType -
     */
    public void sets3FileType(String s3FileType) {
        this.s3FileType = s3FileType;
    }

    /**
     *
     * @return -
     */
    public Employee getEmployee() {
        return this.employee;
    }

    /**
     *
     * @param employee -
     * @return -
     */
    public File employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    /**
     *
     * @param employee -
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

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
        if (!(o instanceof File)) {
            return false;
        }
        return id != null && id.equals(((File) o).id);
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
        return "File{" +
            "id=" + getId() +
            ", s3FileKey='" + gets3FileKey() + "'" +
            ", s3FileType='" + gets3FileKey() + "'" +
            "}";
    }
}
