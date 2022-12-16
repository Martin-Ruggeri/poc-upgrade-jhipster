package com.pae.well.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WellChangeExtractionMethod.
 */
@Entity
@Table(name = "well_change_extraction_method")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WellChangeExtractionMethod implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private ZonedDateTime date;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "saltWaterInjectionPlant", "petroleumPlant", "gasPlant", "project", "rig", "battery", "district", "wellStatus" },
        allowSetters = true
    )
    private Well well;

    @ManyToOne(optional = false)
    @NotNull
    private ExtractionMethod extractionMethod;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public WellChangeExtractionMethod id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return this.date;
    }

    public WellChangeExtractionMethod date(ZonedDateTime date) {
        this.setDate(date);
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Well getWell() {
        return this.well;
    }

    public void setWell(Well well) {
        this.well = well;
    }

    public WellChangeExtractionMethod well(Well well) {
        this.setWell(well);
        return this;
    }

    public ExtractionMethod getExtractionMethod() {
        return this.extractionMethod;
    }

    public void setExtractionMethod(ExtractionMethod extractionMethod) {
        this.extractionMethod = extractionMethod;
    }

    public WellChangeExtractionMethod extractionMethod(ExtractionMethod extractionMethod) {
        this.setExtractionMethod(extractionMethod);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WellChangeExtractionMethod)) {
            return false;
        }
        return id != null && id.equals(((WellChangeExtractionMethod) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WellChangeExtractionMethod{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
