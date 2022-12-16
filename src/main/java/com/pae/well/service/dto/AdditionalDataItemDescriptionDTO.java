package com.pae.well.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.pae.well.domain.AdditionalDataItemDescription} entity.
 */
public class AdditionalDataItemDescriptionDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String description;

    private Boolean areRequiredDateUserAndComment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAreRequiredDateUserAndComment() {
        return areRequiredDateUserAndComment;
    }

    public void setAreRequiredDateUserAndComment(Boolean areRequiredDateUserAndComment) {
        this.areRequiredDateUserAndComment = areRequiredDateUserAndComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdditionalDataItemDescriptionDTO)) {
            return false;
        }

        AdditionalDataItemDescriptionDTO additionalDataItemDescriptionDTO = (AdditionalDataItemDescriptionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, additionalDataItemDescriptionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdditionalDataItemDescriptionDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", areRequiredDateUserAndComment='" + getAreRequiredDateUserAndComment() + "'" +
            "}";
    }
}
