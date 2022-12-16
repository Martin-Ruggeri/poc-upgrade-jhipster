package com.pae.well.service.impl;

import com.pae.well.domain.AdditionalData;
import com.pae.well.repository.AdditionalDataRepository;
import com.pae.well.service.AdditionalDataService;
import com.pae.well.service.dto.AdditionalDataDTO;
import com.pae.well.service.mapper.AdditionalDataMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdditionalData}.
 */
@Service
@Transactional
public class AdditionalDataServiceImpl implements AdditionalDataService {

    private final Logger log = LoggerFactory.getLogger(AdditionalDataServiceImpl.class);

    private final AdditionalDataRepository additionalDataRepository;

    private final AdditionalDataMapper additionalDataMapper;

    public AdditionalDataServiceImpl(AdditionalDataRepository additionalDataRepository, AdditionalDataMapper additionalDataMapper) {
        this.additionalDataRepository = additionalDataRepository;
        this.additionalDataMapper = additionalDataMapper;
    }

    @Override
    public AdditionalDataDTO save(AdditionalDataDTO additionalDataDTO) {
        log.debug("Request to save AdditionalData : {}", additionalDataDTO);
        AdditionalData additionalData = additionalDataMapper.toEntity(additionalDataDTO);
        additionalData = additionalDataRepository.save(additionalData);
        return additionalDataMapper.toDto(additionalData);
    }

    @Override
    public AdditionalDataDTO update(AdditionalDataDTO additionalDataDTO) {
        log.debug("Request to save AdditionalData : {}", additionalDataDTO);
        AdditionalData additionalData = additionalDataMapper.toEntity(additionalDataDTO);
        additionalData = additionalDataRepository.save(additionalData);
        return additionalDataMapper.toDto(additionalData);
    }

    @Override
    public Optional<AdditionalDataDTO> partialUpdate(AdditionalDataDTO additionalDataDTO) {
        log.debug("Request to partially update AdditionalData : {}", additionalDataDTO);

        return additionalDataRepository
            .findById(additionalDataDTO.getId())
            .map(existingAdditionalData -> {
                additionalDataMapper.partialUpdate(existingAdditionalData, additionalDataDTO);

                return existingAdditionalData;
            })
            .map(additionalDataRepository::save)
            .map(additionalDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdditionalDataDTO> findAll() {
        log.debug("Request to get all AdditionalData");
        return additionalDataRepository
            .findAll()
            .stream()
            .map(additionalDataMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdditionalDataDTO> findOne(Long id) {
        log.debug("Request to get AdditionalData : {}", id);
        return additionalDataRepository.findById(id).map(additionalDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdditionalData : {}", id);
        additionalDataRepository.deleteById(id);
    }
}
