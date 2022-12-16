package com.pae.well.service.mapper;

import com.pae.well.domain.WellStatus;
import com.pae.well.service.dto.WellStatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WellStatus} and its DTO {@link WellStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface WellStatusMapper extends EntityMapper<WellStatusDTO, WellStatus> {}
