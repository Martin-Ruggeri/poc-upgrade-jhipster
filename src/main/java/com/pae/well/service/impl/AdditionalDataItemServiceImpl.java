package com.pae.well.service.impl;

import com.pae.well.domain.AdditionalDataItem;
import com.pae.well.repository.AdditionalDataItemRepository;
import com.pae.well.service.AdditionalDataItemService;
import com.pae.well.service.dto.AdditionalDataItemDTO;
import com.pae.well.service.mapper.AdditionalDataItemMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdditionalDataItem}.
 */
@Service
@Transactional
public class AdditionalDataItemServiceImpl implements AdditionalDataItemService {

    private final Logger log = LoggerFactory.getLogger(AdditionalDataItemServiceImpl.class);

    private final AdditionalDataItemRepository additionalDataItemRepository;

    private final AdditionalDataItemMapper additionalDataItemMapper;

    public AdditionalDataItemServiceImpl(
        AdditionalDataItemRepository additionalDataItemRepository,
        AdditionalDataItemMapper additionalDataItemMapper
    ) {
        this.additionalDataItemRepository = additionalDataItemRepository;
        this.additionalDataItemMapper = additionalDataItemMapper;
    }

    @Override
    public AdditionalDataItemDTO save(AdditionalDataItemDTO additionalDataItemDTO) {
        log.debug("Request to save AdditionalDataItem : {}", additionalDataItemDTO);
        AdditionalDataItem additionalDataItem = additionalDataItemMapper.toEntity(additionalDataItemDTO);
        additionalDataItem = additionalDataItemRepository.save(additionalDataItem);
        return additionalDataItemMapper.toDto(additionalDataItem);
    }

    @Override
    public AdditionalDataItemDTO update(AdditionalDataItemDTO additionalDataItemDTO) {
        log.debug("Request to update AdditionalDataItem : {}", additionalDataItemDTO);
        AdditionalDataItem additionalDataItem = additionalDataItemMapper.toEntity(additionalDataItemDTO);
        additionalDataItem = additionalDataItemRepository.save(additionalDataItem);
        return additionalDataItemMapper.toDto(additionalDataItem);
    }

    @Override
    public Optional<AdditionalDataItemDTO> partialUpdate(AdditionalDataItemDTO additionalDataItemDTO) {
        log.debug("Request to partially update AdditionalDataItem : {}", additionalDataItemDTO);

        return additionalDataItemRepository
            .findById(additionalDataItemDTO.getId())
            .map(existingAdditionalDataItem -> {
                additionalDataItemMapper.partialUpdate(existingAdditionalDataItem, additionalDataItemDTO);

                return existingAdditionalDataItem;
            })
            .map(additionalDataItemRepository::save)
            .map(additionalDataItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdditionalDataItemDTO> findAll() {
        log.debug("Request to get all AdditionalDataItems");
        return additionalDataItemRepository
            .findAll()
            .stream()
            .map(additionalDataItemMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdditionalDataItemDTO> findOne(Long id) {
        log.debug("Request to get AdditionalDataItem : {}", id);
        return additionalDataItemRepository.findById(id).map(additionalDataItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdditionalDataItem : {}", id);
        additionalDataItemRepository.deleteById(id);
    }
}
