package com.pae.well.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.pae.well.domain.WellStatus} entity.
 */
@Schema(description = "Estado del pozo")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WellStatusDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
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
        if (!(o instanceof WellStatusDTO)) {
            return false;
        }

        WellStatusDTO wellStatusDTO = (WellStatusDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, wellStatusDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WellStatusDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
