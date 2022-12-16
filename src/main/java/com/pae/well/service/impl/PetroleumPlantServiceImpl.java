package com.pae.well.service.impl;

import com.pae.well.domain.PetroleumPlant;
import com.pae.well.repository.PetroleumPlantRepository;
import com.pae.well.service.PetroleumPlantService;
import com.pae.well.service.dto.PetroleumPlantDTO;
import com.pae.well.service.mapper.PetroleumPlantMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PetroleumPlant}.
 */
@Service
@Transactional
public class PetroleumPlantServiceImpl implements PetroleumPlantService {

    private final Logger log = LoggerFactory.getLogger(PetroleumPlantServiceImpl.class);

    private final PetroleumPlantRepository petroleumPlantRepository;

    private final PetroleumPlantMapper petroleumPlantMapper;

    public PetroleumPlantServiceImpl(PetroleumPlantRepository petroleumPlantRepository, PetroleumPlantMapper petroleumPlantMapper) {
        this.petroleumPlantRepository = petroleumPlantRepository;
        this.petroleumPlantMapper = petroleumPlantMapper;
    }

    @Override
    public PetroleumPlantDTO save(PetroleumPlantDTO petroleumPlantDTO) {
        log.debug("Request to save PetroleumPlant : {}", petroleumPlantDTO);
        PetroleumPlant petroleumPlant = petroleumPlantMapper.toEntity(petroleumPlantDTO);
        petroleumPlant = petroleumPlantRepository.save(petroleumPlant);
        return petroleumPlantMapper.toDto(petroleumPlant);
    }

    @Override
    public PetroleumPlantDTO update(PetroleumPlantDTO petroleumPlantDTO) {
        log.debug("Request to save PetroleumPlant : {}", petroleumPlantDTO);
        PetroleumPlant petroleumPlant = petroleumPlantMapper.toEntity(petroleumPlantDTO);
        petroleumPlant = petroleumPlantRepository.save(petroleumPlant);
        return petroleumPlantMapper.toDto(petroleumPlant);
    }

    @Override
    public Optional<PetroleumPlantDTO> partialUpdate(PetroleumPlantDTO petroleumPlantDTO) {
        log.debug("Request to partially update PetroleumPlant : {}", petroleumPlantDTO);

        return petroleumPlantRepository
            .findById(petroleumPlantDTO.getId())
            .map(existingPetroleumPlant -> {
                petroleumPlantMapper.partialUpdate(existingPetroleumPlant, petroleumPlantDTO);

                return existingPetroleumPlant;
            })
            .map(petroleumPlantRepository::save)
            .map(petroleumPlantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PetroleumPlantDTO> findAll() {
        log.debug("Request to get all PetroleumPlants");
        return petroleumPlantRepository
            .findAll()
            .stream()
            .map(petroleumPlantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PetroleumPlantDTO> findOne(Long id) {
        log.debug("Request to get PetroleumPlant : {}", id);
        return petroleumPlantRepository.findById(id).map(petroleumPlantMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PetroleumPlant : {}", id);
        petroleumPlantRepository.deleteById(id);
    }
}
