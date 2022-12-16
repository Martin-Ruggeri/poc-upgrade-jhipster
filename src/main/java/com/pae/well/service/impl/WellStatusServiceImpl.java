package com.pae.well.service.impl;

import com.pae.well.domain.WellStatus;
import com.pae.well.repository.WellStatusRepository;
import com.pae.well.service.WellStatusService;
import com.pae.well.service.dto.WellStatusDTO;
import com.pae.well.service.mapper.WellStatusMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WellStatus}.
 */
@Service
@Transactional
public class WellStatusServiceImpl implements WellStatusService {

    private final Logger log = LoggerFactory.getLogger(WellStatusServiceImpl.class);

    private final WellStatusRepository wellStatusRepository;

    private final WellStatusMapper wellStatusMapper;

    public WellStatusServiceImpl(WellStatusRepository wellStatusRepository, WellStatusMapper wellStatusMapper) {
        this.wellStatusRepository = wellStatusRepository;
        this.wellStatusMapper = wellStatusMapper;
    }

    @Override
    public WellStatusDTO save(WellStatusDTO wellStatusDTO) {
        log.debug("Request to save WellStatus : {}", wellStatusDTO);
        WellStatus wellStatus = wellStatusMapper.toEntity(wellStatusDTO);
        wellStatus = wellStatusRepository.save(wellStatus);
        return wellStatusMapper.toDto(wellStatus);
    }

    @Override
    public WellStatusDTO update(WellStatusDTO wellStatusDTO) {
        log.debug("Request to save WellStatus : {}", wellStatusDTO);
        WellStatus wellStatus = wellStatusMapper.toEntity(wellStatusDTO);
        wellStatus = wellStatusRepository.save(wellStatus);
        return wellStatusMapper.toDto(wellStatus);
    }

    @Override
    public Optional<WellStatusDTO> partialUpdate(WellStatusDTO wellStatusDTO) {
        log.debug("Request to partially update WellStatus : {}", wellStatusDTO);

        return wellStatusRepository
            .findById(wellStatusDTO.getId())
            .map(existingWellStatus -> {
                wellStatusMapper.partialUpdate(existingWellStatus, wellStatusDTO);

                return existingWellStatus;
            })
            .map(wellStatusRepository::save)
            .map(wellStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WellStatusDTO> findAll() {
        log.debug("Request to get all WellStatuses");
        return wellStatusRepository.findAll().stream().map(wellStatusMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WellStatusDTO> findOne(Long id) {
        log.debug("Request to get WellStatus : {}", id);
        return wellStatusRepository.findById(id).map(wellStatusMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WellStatus : {}", id);
        wellStatusRepository.deleteById(id);
    }
}
