package ie.lyit.app.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * An authority (a security role) used by Spring Security.
 */
@Entity
@Table(name = "jhi_authority")
public class Authority implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * name
     */
    @NotNull
    @Size(max = 50)
    @Id
    @Column(length = 50)
    private String name;

    /**
     * Get name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * @param name name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Equals method
     * @param o object to compare to
     * @return true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Authority)) {
            return false;
        }
        return Objects.equals(name, ((Authority) o).name);
    }

    /**
     * Hashcode method
     * @return unique hashcode value
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    /**
     * toString method
     * @return string for object
     */
    // prettier-ignore
    @Override
    public String toString() {
        return "Authority{" +
            "name='" + name + '\'' +
            "}";
    }
}
