package com.pae.well.service.impl;

import com.pae.well.domain.GasPlant;
import com.pae.well.repository.GasPlantRepository;
import com.pae.well.service.GasPlantService;
import com.pae.well.service.dto.GasPlantDTO;
import com.pae.well.service.mapper.GasPlantMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GasPlant}.
 */
@Service
@Transactional
public class GasPlantServiceImpl implements GasPlantService {

    private final Logger log = LoggerFactory.getLogger(GasPlantServiceImpl.class);

    private final GasPlantRepository gasPlantRepository;

    private final GasPlantMapper gasPlantMapper;

    public GasPlantServiceImpl(GasPlantRepository gasPlantRepository, GasPlantMapper gasPlantMapper) {
        this.gasPlantRepository = gasPlantRepository;
        this.gasPlantMapper = gasPlantMapper;
    }

    @Override
    public GasPlantDTO save(GasPlantDTO gasPlantDTO) {
        log.debug("Request to save GasPlant : {}", gasPlantDTO);
        GasPlant gasPlant = gasPlantMapper.toEntity(gasPlantDTO);
        gasPlant = gasPlantRepository.save(gasPlant);
        return gasPlantMapper.toDto(gasPlant);
    }

    @Override
    public GasPlantDTO update(GasPlantDTO gasPlantDTO) {
        log.debug("Request to update GasPlant : {}", gasPlantDTO);
        GasPlant gasPlant = gasPlantMapper.toEntity(gasPlantDTO);
        gasPlant = gasPlantRepository.save(gasPlant);
        return gasPlantMapper.toDto(gasPlant);
    }

    @Override
    public Optional<GasPlantDTO> partialUpdate(GasPlantDTO gasPlantDTO) {
        log.debug("Request to partially update GasPlant : {}", gasPlantDTO);

        return gasPlantRepository
            .findById(gasPlantDTO.getId())
            .map(existingGasPlant -> {
                gasPlantMapper.partialUpdate(existingGasPlant, gasPlantDTO);

                return existingGasPlant;
            })
            .map(gasPlantRepository::save)
            .map(gasPlantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GasPlantDTO> findAll() {
        log.debug("Request to get all GasPlants");
        return gasPlantRepository.findAll().stream().map(gasPlantMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GasPlantDTO> findOne(Long id) {
        log.debug("Request to get GasPlant : {}", id);
        return gasPlantRepository.findById(id).map(gasPlantMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GasPlant : {}", id);
        gasPlantRepository.deleteById(id);
    }
}
