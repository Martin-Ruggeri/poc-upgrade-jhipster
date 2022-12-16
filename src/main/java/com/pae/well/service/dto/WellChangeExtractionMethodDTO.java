package com.pae.well.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.pae.well.domain.WellChangeExtractionMethod} entity.
 */
public class WellChangeExtractionMethodDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime date;

    private WellDTO well;

    private ExtractionMethodDTO extractionMethod;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public WellDTO getWell() {
        return well;
    }

    public void setWell(WellDTO well) {
        this.well = well;
    }

    public ExtractionMethodDTO getExtractionMethod() {
        return extractionMethod;
    }

    public void setExtractionMethod(ExtractionMethodDTO extractionMethod) {
        this.extractionMethod = extractionMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WellChangeExtractionMethodDTO)) {
            return false;
        }

        WellChangeExtractionMethodDTO wellChangeExtractionMethodDTO = (WellChangeExtractionMethodDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, wellChangeExtractionMethodDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WellChangeExtractionMethodDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", well=" + getWell() +
            ", extractionMethod=" + getExtractionMethod() +
            "}";
    }
}
