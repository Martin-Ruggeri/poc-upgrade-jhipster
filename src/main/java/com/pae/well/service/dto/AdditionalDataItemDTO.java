package com.pae.well.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.pae.well.domain.AdditionalDataItem} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdditionalDataItemDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer idUser;

    @NotNull
    private LocalDate date;

    @Size(max = 2000)
    private String comment;

    private AdditionalDataDTO additionalData;

    private AdditionalDataItemDescriptionDTO additionalDataItemDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public AdditionalDataDTO getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(AdditionalDataDTO additionalData) {
        this.additionalData = additionalData;
    }

    public AdditionalDataItemDescriptionDTO getAdditionalDataItemDescription() {
        return additionalDataItemDescription;
    }

    public void setAdditionalDataItemDescription(AdditionalDataItemDescriptionDTO additionalDataItemDescription) {
        this.additionalDataItemDescription = additionalDataItemDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdditionalDataItemDTO)) {
            return false;
        }

        AdditionalDataItemDTO additionalDataItemDTO = (AdditionalDataItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, additionalDataItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdditionalDataItemDTO{" +
            "id=" + getId() +
            ", idUser=" + getIdUser() +
            ", date='" + getDate() + "'" +
            ", comment='" + getComment() + "'" +
            ", additionalData=" + getAdditionalData() +
            ", additionalDataItemDescription=" + getAdditionalDataItemDescription() +
            "}";
    }
}
