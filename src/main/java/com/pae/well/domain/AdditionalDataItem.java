package com.pae.well.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdditionalDataItem.
 */
@Entity
@Table(name = "additional_data_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdditionalDataItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_user", nullable = false)
    private Integer idUser;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Size(max = 2000)
    @Column(name = "comment", length = 2000)
    private String comment;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "well" }, allowSetters = true)
    private AdditionalData additionalData;

    @ManyToOne(optional = false)
    @NotNull
    private AdditionalDataItemDescription additionalDataItemDescription;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AdditionalDataItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return this.idUser;
    }

    public AdditionalDataItem idUser(Integer idUser) {
        this.setIdUser(idUser);
        return this;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public AdditionalDataItem date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getComment() {
        return this.comment;
    }

    public AdditionalDataItem comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public AdditionalData getAdditionalData() {
        return this.additionalData;
    }

    public void setAdditionalData(AdditionalData additionalData) {
        this.additionalData = additionalData;
    }

    public AdditionalDataItem additionalData(AdditionalData additionalData) {
        this.setAdditionalData(additionalData);
        return this;
    }

    public AdditionalDataItemDescription getAdditionalDataItemDescription() {
        return this.additionalDataItemDescription;
    }

    public void setAdditionalDataItemDescription(AdditionalDataItemDescription additionalDataItemDescription) {
        this.additionalDataItemDescription = additionalDataItemDescription;
    }

    public AdditionalDataItem additionalDataItemDescription(AdditionalDataItemDescription additionalDataItemDescription) {
        this.setAdditionalDataItemDescription(additionalDataItemDescription);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdditionalDataItem)) {
            return false;
        }
        return id != null && id.equals(((AdditionalDataItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdditionalDataItem{" +
            "id=" + getId() +
            ", idUser=" + getIdUser() +
            ", date='" + getDate() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
