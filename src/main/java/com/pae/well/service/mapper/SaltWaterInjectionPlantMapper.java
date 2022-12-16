package com.pae.well.service.mapper;

import com.pae.well.domain.District;
import com.pae.well.domain.SaltWaterInjectionPlant;
import com.pae.well.service.dto.DistrictDTO;
import com.pae.well.service.dto.SaltWaterInjectionPlantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SaltWaterInjectionPlant} and its DTO {@link SaltWaterInjectionPlantDTO}.
 */
@Mapper(componentModel = "spring")
public interface SaltWaterInjectionPlantMapper extends EntityMapper<SaltWaterInjectionPlantDTO, SaltWaterInjectionPlant> {
    @Mapping(target = "district", source = "district", qualifiedByName = "districtId")
    SaltWaterInjectionPlantDTO toDto(SaltWaterInjectionPlant s);

    @Named("districtId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DistrictDTO toDtoDistrictId(District district);
}
