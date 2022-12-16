package com.pae.well.service.impl;

import com.pae.well.domain.Well;
import com.pae.well.repository.WellRepository;
import com.pae.well.service.WellService;
import com.pae.well.service.dto.WellDTO;
import com.pae.well.service.mapper.WellMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Well}.
 */
@Service
@Transactional
public class WellServiceImpl implements WellService {

    private final Logger log = LoggerFactory.getLogger(WellServiceImpl.class);

    private final WellRepository wellRepository;

    private final WellMapper wellMapper;

    public WellServiceImpl(WellRepository wellRepository, WellMapper wellMapper) {
        this.wellRepository = wellRepository;
        this.wellMapper = wellMapper;
    }

    @Override
    public WellDTO save(WellDTO wellDTO) {
        log.debug("Request to save Well : {}", wellDTO);
        Well well = wellMapper.toEntity(wellDTO);
        well = wellRepository.save(well);
        return wellMapper.toDto(well);
    }

    @Override
    public WellDTO update(WellDTO wellDTO) {
        log.debug("Request to save Well : {}", wellDTO);
        Well well = wellMapper.toEntity(wellDTO);
        well = wellRepository.save(well);
        return wellMapper.toDto(well);
    }

    @Override
    public Optional<WellDTO> partialUpdate(WellDTO wellDTO) {
        log.debug("Request to partially update Well : {}", wellDTO);

        return wellRepository
            .findById(wellDTO.getId())
            .map(existingWell -> {
                wellMapper.partialUpdate(existingWell, wellDTO);

                return existingWell;
            })
            .map(wellRepository::save)
            .map(wellMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WellDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Wells");
        return wellRepository.findAll(pageable).map(wellMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WellDTO> findOne(Long id) {
        log.debug("Request to get Well : {}", id);
        return wellRepository.findById(id).map(wellMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Well : {}", id);
        wellRepository.deleteById(id);
    }
}
