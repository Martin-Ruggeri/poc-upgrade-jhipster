package com.pae.well.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.pae.well.domain.AdditionalData} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdditionalDataDTO implements Serializable {

    private Long id;

    @Size(max = 2000)
    private String generalComment;

    private WellDTO well;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGeneralComment() {
        return generalComment;
    }

    public void setGeneralComment(String generalComment) {
        this.generalComment = generalComment;
    }

    public WellDTO getWell() {
        return well;
    }

    public void setWell(WellDTO well) {
        this.well = well;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdditionalDataDTO)) {
            return false;
        }

        AdditionalDataDTO additionalDataDTO = (AdditionalDataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, additionalDataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdditionalDataDTO{" +
            "id=" + getId() +
            ", generalComment='" + getGeneralComment() + "'" +
            ", well=" + getWell() +
            "}";
    }
}
