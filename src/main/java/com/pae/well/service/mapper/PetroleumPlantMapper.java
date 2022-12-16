package com.pae.well.service.mapper;

import com.pae.well.domain.District;
import com.pae.well.domain.PetroleumPlant;
import com.pae.well.service.dto.DistrictDTO;
import com.pae.well.service.dto.PetroleumPlantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PetroleumPlant} and its DTO {@link PetroleumPlantDTO}.
 */
@Mapper(componentModel = "spring")
public interface PetroleumPlantMapper extends EntityMapper<PetroleumPlantDTO, PetroleumPlant> {
    @Mapping(target = "district", source = "district", qualifiedByName = "districtId")
    PetroleumPlantDTO toDto(PetroleumPlant s);

    @Named("districtId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DistrictDTO toDtoDistrictId(District district);
}
