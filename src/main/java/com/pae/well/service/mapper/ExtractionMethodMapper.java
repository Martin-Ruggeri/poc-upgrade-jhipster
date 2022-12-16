package com.pae.well.service.mapper;

import com.pae.well.domain.ExtractionMethod;
import com.pae.well.service.dto.ExtractionMethodDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExtractionMethod} and its DTO {@link ExtractionMethodDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExtractionMethodMapper extends EntityMapper<ExtractionMethodDTO, ExtractionMethod> {}
