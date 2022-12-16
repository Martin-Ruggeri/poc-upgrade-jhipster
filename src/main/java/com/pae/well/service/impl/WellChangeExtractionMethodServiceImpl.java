package com.pae.well.service.impl;

import com.pae.well.domain.WellChangeExtractionMethod;
import com.pae.well.repository.WellChangeExtractionMethodRepository;
import com.pae.well.service.WellChangeExtractionMethodService;
import com.pae.well.service.dto.WellChangeExtractionMethodDTO;
import com.pae.well.service.mapper.WellChangeExtractionMethodMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WellChangeExtractionMethod}.
 */
@Service
@Transactional
public class WellChangeExtractionMethodServiceImpl implements WellChangeExtractionMethodService {

    private final Logger log = LoggerFactory.getLogger(WellChangeExtractionMethodServiceImpl.class);

    private final WellChangeExtractionMethodRepository wellChangeExtractionMethodRepository;

    private final WellChangeExtractionMethodMapper wellChangeExtractionMethodMapper;

    public WellChangeExtractionMethodServiceImpl(
        WellChangeExtractionMethodRepository wellChangeExtractionMethodRepository,
        WellChangeExtractionMethodMapper wellChangeExtractionMethodMapper
    ) {
        this.wellChangeExtractionMethodRepository = wellChangeExtractionMethodRepository;
        this.wellChangeExtractionMethodMapper = wellChangeExtractionMethodMapper;
    }

    @Override
    public WellChangeExtractionMethodDTO save(WellChangeExtractionMethodDTO wellChangeExtractionMethodDTO) {
        log.debug("Request to save WellChangeExtractionMethod : {}", wellChangeExtractionMethodDTO);
        WellChangeExtractionMethod wellChangeExtractionMethod = wellChangeExtractionMethodMapper.toEntity(wellChangeExtractionMethodDTO);
        wellChangeExtractionMethod = wellChangeExtractionMethodRepository.save(wellChangeExtractionMethod);
        return wellChangeExtractionMethodMapper.toDto(wellChangeExtractionMethod);
    }

    @Override
    public WellChangeExtractionMethodDTO update(WellChangeExtractionMethodDTO wellChangeExtractionMethodDTO) {
        log.debug("Request to save WellChangeExtractionMethod : {}", wellChangeExtractionMethodDTO);
        WellChangeExtractionMethod wellChangeExtractionMethod = wellChangeExtractionMethodMapper.toEntity(wellChangeExtractionMethodDTO);
        wellChangeExtractionMethod = wellChangeExtractionMethodRepository.save(wellChangeExtractionMethod);
        return wellChangeExtractionMethodMapper.toDto(wellChangeExtractionMethod);
    }

    @Override
    public Optional<WellChangeExtractionMethodDTO> partialUpdate(WellChangeExtractionMethodDTO wellChangeExtractionMethodDTO) {
        log.debug("Request to partially update WellChangeExtractionMethod : {}", wellChangeExtractionMethodDTO);

        return wellChangeExtractionMethodRepository
            .findById(wellChangeExtractionMethodDTO.getId())
            .map(existingWellChangeExtractionMethod -> {
                wellChangeExtractionMethodMapper.partialUpdate(existingWellChangeExtractionMethod, wellChangeExtractionMethodDTO);

                return existingWellChangeExtractionMethod;
            })
            .map(wellChangeExtractionMethodRepository::save)
            .map(wellChangeExtractionMethodMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WellChangeExtractionMethodDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WellChangeExtractionMethods");
        return wellChangeExtractionMethodRepository.findAll(pageable).map(wellChangeExtractionMethodMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WellChangeExtractionMethodDTO> findOne(Long id) {
        log.debug("Request to get WellChangeExtractionMethod : {}", id);
        return wellChangeExtractionMethodRepository.findById(id).map(wellChangeExtractionMethodMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WellChangeExtractionMethod : {}", id);
        wellChangeExtractionMethodRepository.deleteById(id);
    }
}
