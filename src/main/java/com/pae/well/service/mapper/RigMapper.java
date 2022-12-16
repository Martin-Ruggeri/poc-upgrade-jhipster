package com.pae.well.service.mapper;

import com.pae.well.domain.ManagementUnit;
import com.pae.well.domain.Rig;
import com.pae.well.service.dto.ManagementUnitDTO;
import com.pae.well.service.dto.RigDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rig} and its DTO {@link RigDTO}.
 */
@Mapper(componentModel = "spring")
public interface RigMapper extends EntityMapper<RigDTO, Rig> {
    @Mapping(target = "managementUnit", source = "managementUnit", qualifiedByName = "managementUnitId")
    RigDTO toDto(Rig s);

    @Named("managementUnitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ManagementUnitDTO toDtoManagementUnitId(ManagementUnit managementUnit);
}
