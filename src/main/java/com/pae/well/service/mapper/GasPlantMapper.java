package com.pae.well.service.mapper;

import com.pae.well.domain.District;
import com.pae.well.domain.GasPlant;
import com.pae.well.service.dto.DistrictDTO;
import com.pae.well.service.dto.GasPlantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GasPlant} and its DTO {@link GasPlantDTO}.
 */
@Mapper(componentModel = "spring")
public interface GasPlantMapper extends EntityMapper<GasPlantDTO, GasPlant> {
    @Mapping(target = "district", source = "district", qualifiedByName = "districtId")
    GasPlantDTO toDto(GasPlant s);

    @Named("districtId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DistrictDTO toDtoDistrictId(District district);
}
