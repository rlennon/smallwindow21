package ie.lyit.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ie.lyit.app.domain.Employee} entity. This class is used
 * in {@link ie.lyit.app.web.rest.EmployeeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter email;

    private StringFilter s3ImageKey;

    private LongFilter fileId;

    private LongFilter skillId;

    public EmployeeCriteria() {}

    public EmployeeCriteria(EmployeeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.s3ImageKey = other.s3ImageKey == null ? null : other.s3ImageKey.copy();
        this.fileId = other.fileId == null ? null : other.fileId.copy();
        this.skillId = other.skillId == null ? null : other.skillId.copy();
    }

    @Override
    public EmployeeCriteria copy() {
        return new EmployeeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public StringFilter firstName() {
        if (firstName == null) {
            firstName = new StringFilter();
        }
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public StringFilter lastName() {
        if (lastName == null) {
            lastName = new StringFilter();
        }
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter gets3ImageKey() {
        return s3ImageKey;
    }

    public StringFilter s3ImageKey() {
        if (s3ImageKey == null) {
            s3ImageKey = new StringFilter();
        }
        return s3ImageKey;
    }

    public void sets3ImageKey(StringFilter s3ImageKey) {
        this.s3ImageKey = s3ImageKey;
    }

    public LongFilter getFileId() {
        return fileId;
    }

    public LongFilter fileId() {
        if (fileId == null) {
            fileId = new LongFilter();
        }
        return fileId;
    }

    public void setFileId(LongFilter fileId) {
        this.fileId = fileId;
    }

    public LongFilter getSkillId() {
        return skillId;
    }

    public LongFilter skillId() {
        if (skillId == null) {
            skillId = new LongFilter();
        }
        return skillId;
    }

    public void setSkillId(LongFilter skillId) {
        this.skillId = skillId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeeCriteria that = (EmployeeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(email, that.email) &&
            Objects.equals(s3ImageKey, that.s3ImageKey) &&
            Objects.equals(fileId, that.fileId) &&
            Objects.equals(skillId, that.skillId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, s3ImageKey, fileId, skillId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (firstName != null ? "firstName=" + firstName + ", " : "") +
            (lastName != null ? "lastName=" + lastName + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (s3ImageKey != null ? "s3ImageKey=" + s3ImageKey + ", " : "") +
            (fileId != null ? "fileId=" + fileId + ", " : "") +
            (skillId != null ? "skillId=" + skillId + ", " : "") +
            "}";
    }
}
