package com.pae.well.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.pae.well.domain.Battery} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BatteryDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

    private DistrictDTO district;

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

    public DistrictDTO getDistrict() {
        return district;
    }

    public void setDistrict(DistrictDTO district) {
        this.district = district;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BatteryDTO)) {
            return false;
        }

        BatteryDTO batteryDTO = (BatteryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, batteryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BatteryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", district=" + getDistrict() +
            "}";
    }
}
