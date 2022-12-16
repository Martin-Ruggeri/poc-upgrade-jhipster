package com.pae.well.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.pae.well.domain.ExtractionMethod} entity.
 */
public class ExtractionMethodDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExtractionMethodDTO)) {
            return false;
        }

        ExtractionMethodDTO extractionMethodDTO = (ExtractionMethodDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, extractionMethodDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExtractionMethodDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
