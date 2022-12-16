package com.pae.well.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdditionalDataItemDescription.
 */
@Entity
@Table(name = "additional_data_item_description")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdditionalDataItemDescription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "description", length = 100, nullable = false)
    private String description;

    @Column(name = "are_required_date_user_and_comment")
    private Boolean areRequiredDateUserAndComment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AdditionalDataItemDescription id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public AdditionalDataItemDescription description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAreRequiredDateUserAndComment() {
        return this.areRequiredDateUserAndComment;
    }

    public AdditionalDataItemDescription areRequiredDateUserAndComment(Boolean areRequiredDateUserAndComment) {
        this.setAreRequiredDateUserAndComment(areRequiredDateUserAndComment);
        return this;
    }

    public void setAreRequiredDateUserAndComment(Boolean areRequiredDateUserAndComment) {
        this.areRequiredDateUserAndComment = areRequiredDateUserAndComment;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdditionalDataItemDescription)) {
            return false;
        }
        return id != null && id.equals(((AdditionalDataItemDescription) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdditionalDataItemDescription{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", areRequiredDateUserAndComment='" + getAreRequiredDateUserAndComment() + "'" +
            "}";
    }
}
