package com.pae.well.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.pae.well.domain.Rig} entity.
 */
public class RigDTO implements Serializable {

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
        if (!(o instanceof RigDTO)) {
            return false;
        }

        RigDTO rigDTO = (RigDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rigDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RigDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", managementUnit=" + getManagementUnit() +
            "}";
    }
}
