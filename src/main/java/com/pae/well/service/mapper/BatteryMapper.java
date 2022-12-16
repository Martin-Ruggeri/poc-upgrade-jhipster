package com.pae.well.service.mapper;

import com.pae.well.domain.Battery;
import com.pae.well.domain.District;
import com.pae.well.service.dto.BatteryDTO;
import com.pae.well.service.dto.DistrictDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Battery} and its DTO {@link BatteryDTO}.
 */
@Mapper(componentModel = "spring")
public interface BatteryMapper extends EntityMapper<BatteryDTO, Battery> {
    @Mapping(target = "district", source = "district", qualifiedByName = "districtId")
    BatteryDTO toDto(Battery s);

    @Named("districtId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DistrictDTO toDtoDistrictId(District district);
}
