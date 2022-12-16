package com.pae.well.service.impl;

import com.pae.well.domain.ManagementUnit;
import com.pae.well.repository.ManagementUnitRepository;
import com.pae.well.service.ManagementUnitService;
import com.pae.well.service.dto.ManagementUnitDTO;
import com.pae.well.service.mapper.ManagementUnitMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ManagementUnit}.
 */
@Service
@Transactional
public class ManagementUnitServiceImpl implements ManagementUnitService {

    private final Logger log = LoggerFactory.getLogger(ManagementUnitServiceImpl.class);

    private final ManagementUnitRepository managementUnitRepository;

    private final ManagementUnitMapper managementUnitMapper;

    public ManagementUnitServiceImpl(ManagementUnitRepository managementUnitRepository, ManagementUnitMapper managementUnitMapper) {
        this.managementUnitRepository = managementUnitRepository;
        this.managementUnitMapper = managementUnitMapper;
    }

    @Override
    public ManagementUnitDTO save(ManagementUnitDTO managementUnitDTO) {
        log.debug("Request to save ManagementUnit : {}", managementUnitDTO);
        ManagementUnit managementUnit = managementUnitMapper.toEntity(managementUnitDTO);
        managementUnit = managementUnitRepository.save(managementUnit);
        return managementUnitMapper.toDto(managementUnit);
    }

    @Override
    public ManagementUnitDTO update(ManagementUnitDTO managementUnitDTO) {
        log.debug("Request to update ManagementUnit : {}", managementUnitDTO);
        ManagementUnit managementUnit = managementUnitMapper.toEntity(managementUnitDTO);
        managementUnit = managementUnitRepository.save(managementUnit);
        return managementUnitMapper.toDto(managementUnit);
    }

    @Override
    public Optional<ManagementUnitDTO> partialUpdate(ManagementUnitDTO managementUnitDTO) {
        log.debug("Request to partially update ManagementUnit : {}", managementUnitDTO);

        return managementUnitRepository
            .findById(managementUnitDTO.getId())
            .map(existingManagementUnit -> {
                managementUnitMapper.partialUpdate(existingManagementUnit, managementUnitDTO);

                return existingManagementUnit;
            })
            .map(managementUnitRepository::save)
            .map(managementUnitMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManagementUnitDTO> findAll() {
        log.debug("Request to get all ManagementUnits");
        return managementUnitRepository
            .findAll()
            .stream()
            .map(managementUnitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ManagementUnitDTO> findOne(Long id) {
        log.debug("Request to get ManagementUnit : {}", id);
        return managementUnitRepository.findById(id).map(managementUnitMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ManagementUnit : {}", id);
        managementUnitRepository.deleteById(id);
    }
}
