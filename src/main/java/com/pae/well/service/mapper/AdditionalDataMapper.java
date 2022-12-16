package com.pae.well.service.mapper;

import com.pae.well.domain.AdditionalData;
import com.pae.well.domain.Well;
import com.pae.well.service.dto.AdditionalDataDTO;
import com.pae.well.service.dto.WellDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdditionalData} and its DTO {@link AdditionalDataDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdditionalDataMapper extends EntityMapper<AdditionalDataDTO, AdditionalData> {
    @Mapping(target = "well", source = "well", qualifiedByName = "wellId")
    AdditionalDataDTO toDto(AdditionalData s);

    @Named("wellId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WellDTO toDtoWellId(Well well);
}
