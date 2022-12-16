package com.pae.well.service.mapper;

import com.pae.well.domain.Note;
import com.pae.well.domain.Well;
import com.pae.well.service.dto.NoteDTO;
import com.pae.well.service.dto.WellDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Note} and its DTO {@link NoteDTO}.
 */
@Mapper(componentModel = "spring")
public interface NoteMapper extends EntityMapper<NoteDTO, Note> {
    @Mapping(target = "well", source = "well", qualifiedByName = "wellId")
    NoteDTO toDto(Note s);

    @Named("wellId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WellDTO toDtoWellId(Well well);
}
