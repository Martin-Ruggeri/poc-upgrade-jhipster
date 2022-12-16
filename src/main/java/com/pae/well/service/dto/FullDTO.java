package com.pae.well.service.dto;

import javax.validation.constraints.*;

public class FullDTO {

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

    // prettier-ignore
    @Override
    public String toString() {
        return "FullDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
