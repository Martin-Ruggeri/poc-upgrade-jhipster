package com.pae.well.service.impl;

import com.pae.well.domain.AdditionalDataItemDescription;
import com.pae.well.repository.AdditionalDataItemDescriptionRepository;
import com.pae.well.service.AdditionalDataItemDescriptionService;
import com.pae.well.service.dto.AdditionalDataItemDescriptionDTO;
import com.pae.well.service.mapper.AdditionalDataItemDescriptionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdditionalDataItemDescription}.
 */
@Service
@Transactional
public class AdditionalDataItemDescriptionServiceImpl implements AdditionalDataItemDescriptionService {

    private final Logger log = LoggerFactory.getLogger(AdditionalDataItemDescriptionServiceImpl.class);

    private final AdditionalDataItemDescriptionRepository additionalDataItemDescriptionRepository;

    private final AdditionalDataItemDescriptionMapper additionalDataItemDescriptionMapper;

    public AdditionalDataItemDescriptionServiceImpl(
        AdditionalDataItemDescriptionRepository additionalDataItemDescriptionRepository,
        AdditionalDataItemDescriptionMapper additionalDataItemDescriptionMapper
    ) {
        this.additionalDataItemDescriptionRepository = additionalDataItemDescriptionRepository;
        this.additionalDataItemDescriptionMapper = additionalDataItemDescriptionMapper;
    }

    @Override
    public AdditionalDataItemDescriptionDTO save(AdditionalDataItemDescriptionDTO additionalDataItemDescriptionDTO) {
        log.debug("Request to save AdditionalDataItemDescription : {}", additionalDataItemDescriptionDTO);
        AdditionalDataItemDescription additionalDataItemDescription = additionalDataItemDescriptionMapper.toEntity(
            additionalDataItemDescriptionDTO
        );
        additionalDataItemDescription = additionalDataItemDescriptionRepository.save(additionalDataItemDescription);
        return additionalDataItemDescriptionMapper.toDto(additionalDataItemDescription);
    }

    @Override
    public AdditionalDataItemDescriptionDTO update(AdditionalDataItemDescriptionDTO additionalDataItemDescriptionDTO) {
        log.debug("Request to save AdditionalDataItemDescription : {}", additionalDataItemDescriptionDTO);
        AdditionalDataItemDescription additionalDataItemDescription = additionalDataItemDescriptionMapper.toEntity(
            additionalDataItemDescriptionDTO
        );
        additionalDataItemDescription = additionalDataItemDescriptionRepository.save(additionalDataItemDescription);
        return additionalDataItemDescriptionMapper.toDto(additionalDataItemDescription);
    }

    @Override
    public Optional<AdditionalDataItemDescriptionDTO> partialUpdate(AdditionalDataItemDescriptionDTO additionalDataItemDescriptionDTO) {
        log.debug("Request to partially update AdditionalDataItemDescription : {}", additionalDataItemDescriptionDTO);

        return additionalDataItemDescriptionRepository
            .findById(additionalDataItemDescriptionDTO.getId())
            .map(existingAdditionalDataItemDescription -> {
                additionalDataItemDescriptionMapper.partialUpdate(existingAdditionalDataItemDescription, additionalDataItemDescriptionDTO);

                return existingAdditionalDataItemDescription;
            })
            .map(additionalDataItemDescriptionRepository::save)
            .map(additionalDataItemDescriptionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdditionalDataItemDescriptionDTO> findAll() {
        log.debug("Request to get all AdditionalDataItemDescriptions");
        return additionalDataItemDescriptionRepository
            .findAll()
            .stream()
            .map(additionalDataItemDescriptionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdditionalDataItemDescriptionDTO> findOne(Long id) {
        log.debug("Request to get AdditionalDataItemDescription : {}", id);
        return additionalDataItemDescriptionRepository.findById(id).map(additionalDataItemDescriptionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdditionalDataItemDescription : {}", id);
        additionalDataItemDescriptionRepository.deleteById(id);
    }
}
