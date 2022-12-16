package com.pae.well.service.mapper;

import com.pae.well.domain.ExtractionMethod;
import com.pae.well.domain.Well;
import com.pae.well.domain.WellChangeExtractionMethod;
import com.pae.well.service.dto.ExtractionMethodDTO;
import com.pae.well.service.dto.WellChangeExtractionMethodDTO;
import com.pae.well.service.dto.WellDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WellChangeExtractionMethod} and its DTO {@link WellChangeExtractionMethodDTO}.
 */
@Mapper(componentModel = "spring")
public interface WellChangeExtractionMethodMapper extends EntityMapper<WellChangeExtractionMethodDTO, WellChangeExtractionMethod> {
    @Mapping(target = "well", source = "well", qualifiedByName = "wellId")
    @Mapping(target = "extractionMethod", source = "extractionMethod", qualifiedByName = "extractionMethodId")
    WellChangeExtractionMethodDTO toDto(WellChangeExtractionMethod s);

    @Named("wellId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WellDTO toDtoWellId(Well well);

    @Named("extractionMethodId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExtractionMethodDTO toDtoExtractionMethodId(ExtractionMethod extractionMethod);
}
