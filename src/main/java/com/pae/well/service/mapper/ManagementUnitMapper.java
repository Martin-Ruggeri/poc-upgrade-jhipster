package com.pae.well.service.mapper;

import com.pae.well.domain.ManagementUnit;
import com.pae.well.service.dto.ManagementUnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ManagementUnit} and its DTO {@link ManagementUnitDTO}.
 */
@Mapper(componentModel = "spring")
public interface ManagementUnitMapper extends EntityMapper<ManagementUnitDTO, ManagementUnit> {}
