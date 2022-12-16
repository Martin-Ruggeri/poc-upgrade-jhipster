package com.pae.well.service.mapper;

import com.pae.well.domain.AdditionalData;
import com.pae.well.domain.AdditionalDataItem;
import com.pae.well.domain.AdditionalDataItemDescription;
import com.pae.well.service.dto.AdditionalDataDTO;
import com.pae.well.service.dto.AdditionalDataItemDTO;
import com.pae.well.service.dto.AdditionalDataItemDescriptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdditionalDataItem} and its DTO {@link AdditionalDataItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdditionalDataItemMapper extends EntityMapper<AdditionalDataItemDTO, AdditionalDataItem> {
    @Mapping(target = "additionalData", source = "additionalData", qualifiedByName = "additionalDataId")
    @Mapping(
        target = "additionalDataItemDescription",
        source = "additionalDataItemDescription",
        qualifiedByName = "additionalDataItemDescriptionId"
    )
    AdditionalDataItemDTO toDto(AdditionalDataItem s);

    @Named("additionalDataId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdditionalDataDTO toDtoAdditionalDataId(AdditionalData additionalData);

    @Named("additionalDataItemDescriptionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AdditionalDataItemDescriptionDTO toDtoAdditionalDataItemDescriptionId(AdditionalDataItemDescription additionalDataItemDescription);
}
