package ie.lyit.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * generated id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * category name
     */
    @NotNull
    @Column(name = "category_name", nullable = false)
    private String categoryName;

    /**
     * assigned skills
     */
    @ManyToMany(mappedBy = "categories")
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
    public Category id(Long id) {
        this.id = id;
        return this;
    }

    /**
     *
     * @return -
     */
    public String getCategoryName() {
        return this.categoryName;
    }

    /**
     *
     * @param categoryName -
     * @return -
     */
    public Category categoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    /**
     *
     * @param categoryName -
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
    public Category skills(Set<Skill> skills) {
        this.setSkills(skills);
        return this;
    }

    /**
     *
     * @param skill -
     * @return -
     */
    public Category addSkill(Skill skill) {
        this.skills.add(skill);
        skill.getCategories().add(this);
        return this;
    }

    /**
     *
     * @param skill -
     * @return -
     */
    public Category removeSkill(Skill skill) {
        this.skills.remove(skill);
        skill.getCategories().remove(this);
        return this;
    }

    /**
     *
     * @param skills -
     */
    public void setSkills(Set<Skill> skills) {
        if (this.skills != null) {
            this.skills.forEach(i -> i.removeCategory(this));
        }
        if (skills != null) {
            skills.forEach(i -> i.addCategory(this));
        }
        this.skills = skills;
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
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
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
        return "Category{" +
            "id=" + getId() +
            ", categoryName='" + getCategoryName() + "'" +
            "}";
    }
}
