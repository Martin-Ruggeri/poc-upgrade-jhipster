package com.pae.well.service.mapper;

import com.pae.well.domain.District;
import com.pae.well.domain.ManagementUnit;
import com.pae.well.service.dto.DistrictDTO;
import com.pae.well.service.dto.ManagementUnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link District} and its DTO {@link DistrictDTO}.
 */
@Mapper(componentModel = "spring")
public interface DistrictMapper extends EntityMapper<DistrictDTO, District> {
    @Mapping(target = "managementUnit", source = "managementUnit", qualifiedByName = "managementUnitId")
    DistrictDTO toDto(District s);

    @Named("managementUnitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ManagementUnitDTO toDtoManagementUnitId(ManagementUnit managementUnit);
}
