package com.pae.well.service.impl;

import com.pae.well.domain.SaltWaterInjectionPlant;
import com.pae.well.repository.SaltWaterInjectionPlantRepository;
import com.pae.well.service.SaltWaterInjectionPlantService;
import com.pae.well.service.dto.SaltWaterInjectionPlantDTO;
import com.pae.well.service.mapper.SaltWaterInjectionPlantMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SaltWaterInjectionPlant}.
 */
@Service
@Transactional
public class SaltWaterInjectionPlantServiceImpl implements SaltWaterInjectionPlantService {

    private final Logger log = LoggerFactory.getLogger(SaltWaterInjectionPlantServiceImpl.class);

    private final SaltWaterInjectionPlantRepository saltWaterInjectionPlantRepository;

    private final SaltWaterInjectionPlantMapper saltWaterInjectionPlantMapper;

    public SaltWaterInjectionPlantServiceImpl(
        SaltWaterInjectionPlantRepository saltWaterInjectionPlantRepository,
        SaltWaterInjectionPlantMapper saltWaterInjectionPlantMapper
    ) {
        this.saltWaterInjectionPlantRepository = saltWaterInjectionPlantRepository;
        this.saltWaterInjectionPlantMapper = saltWaterInjectionPlantMapper;
    }

    @Override
    public SaltWaterInjectionPlantDTO save(SaltWaterInjectionPlantDTO saltWaterInjectionPlantDTO) {
        log.debug("Request to save SaltWaterInjectionPlant : {}", saltWaterInjectionPlantDTO);
        SaltWaterInjectionPlant saltWaterInjectionPlant = saltWaterInjectionPlantMapper.toEntity(saltWaterInjectionPlantDTO);
        saltWaterInjectionPlant = saltWaterInjectionPlantRepository.save(saltWaterInjectionPlant);
        return saltWaterInjectionPlantMapper.toDto(saltWaterInjectionPlant);
    }

    @Override
    public SaltWaterInjectionPlantDTO update(SaltWaterInjectionPlantDTO saltWaterInjectionPlantDTO) {
        log.debug("Request to save SaltWaterInjectionPlant : {}", saltWaterInjectionPlantDTO);
        SaltWaterInjectionPlant saltWaterInjectionPlant = saltWaterInjectionPlantMapper.toEntity(saltWaterInjectionPlantDTO);
        saltWaterInjectionPlant = saltWaterInjectionPlantRepository.save(saltWaterInjectionPlant);
        return saltWaterInjectionPlantMapper.toDto(saltWaterInjectionPlant);
    }

    @Override
    public Optional<SaltWaterInjectionPlantDTO> partialUpdate(SaltWaterInjectionPlantDTO saltWaterInjectionPlantDTO) {
        log.debug("Request to partially update SaltWaterInjectionPlant : {}", saltWaterInjectionPlantDTO);

        return saltWaterInjectionPlantRepository
            .findById(saltWaterInjectionPlantDTO.getId())
            .map(existingSaltWaterInjectionPlant -> {
                saltWaterInjectionPlantMapper.partialUpdate(existingSaltWaterInjectionPlant, saltWaterInjectionPlantDTO);

                return existingSaltWaterInjectionPlant;
            })
            .map(saltWaterInjectionPlantRepository::save)
            .map(saltWaterInjectionPlantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaltWaterInjectionPlantDTO> findAll() {
        log.debug("Request to get all SaltWaterInjectionPlants");
        return saltWaterInjectionPlantRepository
            .findAll()
            .stream()
            .map(saltWaterInjectionPlantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SaltWaterInjectionPlantDTO> findOne(Long id) {
        log.debug("Request to get SaltWaterInjectionPlant : {}", id);
        return saltWaterInjectionPlantRepository.findById(id).map(saltWaterInjectionPlantMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SaltWaterInjectionPlant : {}", id);
        saltWaterInjectionPlantRepository.deleteById(id);
    }
}
