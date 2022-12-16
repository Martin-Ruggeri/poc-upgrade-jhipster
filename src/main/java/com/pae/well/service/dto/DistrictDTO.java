package com.pae.well.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.pae.well.domain.District} entity.
 */
@Schema(description = "Distrito")
public class DistrictDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

    private ManagementUnitDTO managementUnit;

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

    public ManagementUnitDTO getManagementUnit() {
        return managementUnit;
    }

    public void setManagementUnit(ManagementUnitDTO managementUnit) {
        this.managementUnit = managementUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DistrictDTO)) {
            return false;
        }

        DistrictDTO districtDTO = (DistrictDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, districtDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DistrictDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", managementUnit=" + getManagementUnit() +
            "}";
    }
}
