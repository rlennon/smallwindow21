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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "s_3_file_key")
    private String s3FileKey;

    @Column(name = "s_3_file_type")
    private String s3FileType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "files", "skills" }, allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public File id(Long id) {
        this.id = id;
        return this;
    }

    public String gets3FileKey() {
        return this.s3FileKey;
    }

    public String gets3FileType() {
        return this.s3FileType;
    }

    public File s3FileKey(String s3FileKey) {
        this.s3FileKey = s3FileKey;
        return this;
    }

    public File s3FileType(String s3FileType) {
        this.s3FileType = s3FileType;
        return this;
    }

    public void sets3FileKey(String s3FileKey) {
        this.s3FileKey = s3FileKey;
    }

    public void sets3FileType(String s3FileType) {
        this.s3FileType = s3FileType;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public File employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

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

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

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
