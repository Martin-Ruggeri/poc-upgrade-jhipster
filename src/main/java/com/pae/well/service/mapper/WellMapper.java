package com.pae.well.service.mapper;

import com.pae.well.domain.Battery;
import com.pae.well.domain.District;
import com.pae.well.domain.GasPlant;
import com.pae.well.domain.PetroleumPlant;
import com.pae.well.domain.Project;
import com.pae.well.domain.Rig;
import com.pae.well.domain.SaltWaterInjectionPlant;
import com.pae.well.domain.Well;
import com.pae.well.domain.WellStatus;
import com.pae.well.service.dto.BatteryDTO;
import com.pae.well.service.dto.DistrictDTO;
import com.pae.well.service.dto.GasPlantDTO;
import com.pae.well.service.dto.PetroleumPlantDTO;
import com.pae.well.service.dto.ProjectDTO;
import com.pae.well.service.dto.RigDTO;
import com.pae.well.service.dto.SaltWaterInjectionPlantDTO;
import com.pae.well.service.dto.WellDTO;
import com.pae.well.service.dto.WellStatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Well} and its DTO {@link WellDTO}.
 */
@Mapper(componentModel = "spring")
public interface WellMapper extends EntityMapper<WellDTO, Well> {
    @Mapping(target = "saltWaterInjectionPlant", source = "saltWaterInjectionPlant", qualifiedByName = "saltWaterInjectionPlantId")
    @Mapping(target = "petroleumPlant", source = "petroleumPlant", qualifiedByName = "petroleumPlantId")
    @Mapping(target = "gasPlant", source = "gasPlant", qualifiedByName = "gasPlantId")
    @Mapping(target = "project", source = "project", qualifiedByName = "projectId")
    @Mapping(target = "rig", source = "rig", qualifiedByName = "rigId")
    @Mapping(target = "battery", source = "battery", qualifiedByName = "batteryId")
    @Mapping(target = "district", source = "district", qualifiedByName = "districtId")
    @Mapping(target = "wellStatus", source = "wellStatus", qualifiedByName = "wellStatusId")
    WellDTO toDto(Well s);

    @Named("saltWaterInjectionPlantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SaltWaterInjectionPlantDTO toDtoSaltWaterInjectionPlantId(SaltWaterInjectionPlant saltWaterInjectionPlant);

    @Named("petroleumPlantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PetroleumPlantDTO toDtoPetroleumPlantId(PetroleumPlant petroleumPlant);

    @Named("gasPlantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GasPlantDTO toDtoGasPlantId(GasPlant gasPlant);

    @Named("projectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectDTO toDtoProjectId(Project project);

    @Named("rigId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RigDTO toDtoRigId(Rig rig);

    @Named("batteryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BatteryDTO toDtoBatteryId(Battery battery);

    @Named("districtId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DistrictDTO toDtoDistrictId(District district);

    @Named("wellStatusId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WellStatusDTO toDtoWellStatusId(WellStatus wellStatus);
}
