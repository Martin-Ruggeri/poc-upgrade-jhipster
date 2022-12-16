package com.pae.well.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdditionalData.
 */
@Entity
@Table(name = "additional_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdditionalData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 2000)
    @Column(name = "general_comment", length = 2000)
    private String generalComment;

    @JsonIgnoreProperties(
        value = { "saltWaterInjectionPlant", "petroleumPlant", "gasPlant", "project", "rig", "battery", "district", "wellStatus" },
        allowSetters = true
    )
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Well well;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AdditionalData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGeneralComment() {
        return this.generalComment;
    }

    public AdditionalData generalComment(String generalComment) {
        this.setGeneralComment(generalComment);
        return this;
    }

    public void setGeneralComment(String generalComment) {
        this.generalComment = generalComment;
    }

    public Well getWell() {
        return this.well;
    }

    public void setWell(Well well) {
        this.well = well;
    }

    public AdditionalData well(Well well) {
        this.setWell(well);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdditionalData)) {
            return false;
        }
        return id != null && id.equals(((AdditionalData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdditionalData{" +
            "id=" + getId() +
            ", generalComment='" + getGeneralComment() + "'" +
            "}";
    }
}
