package com.pae.well.service.mapper;

import com.pae.well.domain.AdditionalDataItemDescription;
import com.pae.well.service.dto.AdditionalDataItemDescriptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdditionalDataItemDescription} and its DTO {@link AdditionalDataItemDescriptionDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdditionalDataItemDescriptionMapper
    extends EntityMapper<AdditionalDataItemDescriptionDTO, AdditionalDataItemDescription> {}
