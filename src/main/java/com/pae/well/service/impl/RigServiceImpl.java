package com.pae.well.service.impl;

import com.pae.well.domain.Rig;
import com.pae.well.repository.RigRepository;
import com.pae.well.service.RigService;
import com.pae.well.service.dto.RigDTO;
import com.pae.well.service.mapper.RigMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Rig}.
 */
@Service
@Transactional
public class RigServiceImpl implements RigService {

    private final Logger log = LoggerFactory.getLogger(RigServiceImpl.class);

    private final RigRepository rigRepository;

    private final RigMapper rigMapper;

    public RigServiceImpl(RigRepository rigRepository, RigMapper rigMapper) {
        this.rigRepository = rigRepository;
        this.rigMapper = rigMapper;
    }

    @Override
    public RigDTO save(RigDTO rigDTO) {
        log.debug("Request to save Rig : {}", rigDTO);
        Rig rig = rigMapper.toEntity(rigDTO);
        rig = rigRepository.save(rig);
        return rigMapper.toDto(rig);
    }

    @Override
    public RigDTO update(RigDTO rigDTO) {
        log.debug("Request to save Rig : {}", rigDTO);
        Rig rig = rigMapper.toEntity(rigDTO);
        rig = rigRepository.save(rig);
        return rigMapper.toDto(rig);
    }

    @Override
    public Optional<RigDTO> partialUpdate(RigDTO rigDTO) {
        log.debug("Request to partially update Rig : {}", rigDTO);

        return rigRepository
            .findById(rigDTO.getId())
            .map(existingRig -> {
                rigMapper.partialUpdate(existingRig, rigDTO);

                return existingRig;
            })
            .map(rigRepository::save)
            .map(rigMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RigDTO> findAll() {
        log.debug("Request to get all Rigs");
        return rigRepository.findAll().stream().map(rigMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RigDTO> findOne(Long id) {
        log.debug("Request to get Rig : {}", id);
        return rigRepository.findById(id).map(rigMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rig : {}", id);
        rigRepository.deleteById(id);
    }
}
