package ie.lyit.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Skill.
 */
@Entity
@Table(name = "skill")
public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * generated id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * title
     */
    @Column(name = "title")
    private String title;

    /**
     * description
     */
    @Column(name = "description")
    private String description;

    /**
     * categories assigned
     */
    @ManyToMany
    @JoinTable(
        name = "rel_skill__category",
        joinColumns = @JoinColumn(name = "skill_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnoreProperties(value = { "skills" }, allowSetters = true)
    private Set<Category> categories = new HashSet<>();

    /**
     * employees assigned
     */
    @ManyToMany
    @JoinTable(
        name = "rel_skill__employee",
        joinColumns = @JoinColumn(name = "skill_id"),
        inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    @JsonIgnoreProperties(value = { "files", "skills" }, allowSetters = true)
    private Set<Employee> employees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

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
    public Skill id(Long id) {
        this.id = id;
        return this;
    }

    /**
     *
     * @return -
     */
    public String getTitle() {
        return this.title;
    }

    /**
     *
     * @param title -
     * @return -
     */
    public Skill title(String title) {
        this.title = title;
        return this;
    }

    /**
     *
     * @param title -
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return -
     */
    public String getDescription() {
        return this.description;
    }

    /**
     *
     * @param description -
     * @return -
     */
    public Skill description(String description) {
        this.description = description;
        return this;
    }

    /**
     *
     * @param description -
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return -
     */
    public Set<Category> getCategories() {
        return this.categories;
    }

    /**
     *
     * @param categories -
     * @return -
     */
    public Skill categories(Set<Category> categories) {
        this.setCategories(categories);
        return this;
    }

    /**
     *
     * @param category -
     * @return -
     */
    public Skill addCategory(Category category) {
        this.categories.add(category);
        category.getSkills().add(this);
        return this;
    }

    /**
     *
     * @param category -
     * @return -
     */
    public Skill removeCategory(Category category) {
        this.categories.remove(category);
        category.getSkills().remove(this);
        return this;
    }

    /**
     *
     * @param categories -
     */
    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    /**
     *
     * @return -
     */
    public Set<Employee> getEmployees() {
        return this.employees;
    }

    /**
     *
     * @param employees -
     * @return -
     */
    public Skill employees(Set<Employee> employees) {
        this.setEmployees(employees);
        return this;
    }

    /**
     *
     * @param employee -
     * @return -
     */
    public Skill addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.getSkills().add(this);
        return this;
    }

    /**
     *
     * @param employee -
     * @return -
     */
    public Skill removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.getSkills().remove(this);
        return this;
    }

    /**
     *
     * @param employees -
     */
    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
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
        if (!(o instanceof Skill)) {
            return false;
        }
        return id != null && id.equals(((Skill) o).id);
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
        return "Skill{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
