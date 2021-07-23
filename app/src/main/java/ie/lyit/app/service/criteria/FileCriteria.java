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
 * Criteria class for the {@link ie.lyit.app.domain.File} entity. This class is used
 * in {@link ie.lyit.app.web.rest.FileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /files?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter s3FileKey;

    private LongFilter employeeId;

    public FileCriteria() {}

    public FileCriteria(FileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.s3FileKey = other.s3FileKey == null ? null : other.s3FileKey.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
    }

    @Override
    public FileCriteria copy() {
        return new FileCriteria(this);
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

    public StringFilter gets3FileKey() {
        return s3FileKey;
    }

    public StringFilter s3FileKey() {
        if (s3FileKey == null) {
            s3FileKey = new StringFilter();
        }
        return s3FileKey;
    }

    public void sets3FileKey(StringFilter s3FileKey) {
        this.s3FileKey = s3FileKey;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public LongFilter employeeId() {
        if (employeeId == null) {
            employeeId = new LongFilter();
        }
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FileCriteria that = (FileCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(s3FileKey, that.s3FileKey) && Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, s3FileKey, employeeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (s3FileKey != null ? "s3FileKey=" + s3FileKey + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }
}
