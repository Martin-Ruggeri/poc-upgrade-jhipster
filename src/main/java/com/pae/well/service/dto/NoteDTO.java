package com.pae.well.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.pae.well.domain.Note} entity.
 */
public class NoteDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer idUser;

    @NotNull
    @Size(max = 2000)
    private String description;

    @NotNull
    private LocalDate date;

    private WellDTO well;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
        if (!(o instanceof NoteDTO)) {
            return false;
        }

        NoteDTO noteDTO = (NoteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, noteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NoteDTO{" +
            "id=" + getId() +
            ", idUser=" + getIdUser() +
            ", description='" + getDescription() + "'" +
            ", date='" + getDate() + "'" +
            ", well=" + getWell() +
            "}";
    }
}
