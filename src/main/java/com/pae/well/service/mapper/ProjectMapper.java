package com.pae.well.service.mapper;

import com.pae.well.domain.ManagementUnit;
import com.pae.well.domain.Project;
import com.pae.well.service.dto.ManagementUnitDTO;
import com.pae.well.service.dto.ProjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Project} and its DTO {@link ProjectDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjectMapper extends EntityMapper<ProjectDTO, Project> {
    @Mapping(target = "managementUnit", source = "managementUnit", qualifiedByName = "managementUnitId")
    ProjectDTO toDto(Project s);

    @Named("managementUnitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ManagementUnitDTO toDtoManagementUnitId(ManagementUnit managementUnit);
}
