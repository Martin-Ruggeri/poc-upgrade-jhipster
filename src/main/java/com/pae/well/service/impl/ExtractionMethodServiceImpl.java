package com.pae.well.service.impl;

import com.pae.well.domain.ExtractionMethod;
import com.pae.well.repository.ExtractionMethodRepository;
import com.pae.well.service.ExtractionMethodService;
import com.pae.well.service.dto.ExtractionMethodDTO;
import com.pae.well.service.mapper.ExtractionMethodMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ExtractionMethod}.
 */
@Service
@Transactional
public class ExtractionMethodServiceImpl implements ExtractionMethodService {

    private final Logger log = LoggerFactory.getLogger(ExtractionMethodServiceImpl.class);

    private final ExtractionMethodRepository extractionMethodRepository;

    private final ExtractionMethodMapper extractionMethodMapper;

    public ExtractionMethodServiceImpl(
        ExtractionMethodRepository extractionMethodRepository,
        ExtractionMethodMapper extractionMethodMapper
    ) {
        this.extractionMethodRepository = extractionMethodRepository;
        this.extractionMethodMapper = extractionMethodMapper;
    }

    @Override
    public ExtractionMethodDTO save(ExtractionMethodDTO extractionMethodDTO) {
        log.debug("Request to save ExtractionMethod : {}", extractionMethodDTO);
        ExtractionMethod extractionMethod = extractionMethodMapper.toEntity(extractionMethodDTO);
        extractionMethod = extractionMethodRepository.save(extractionMethod);
        return extractionMethodMapper.toDto(extractionMethod);
    }

    @Override
    public ExtractionMethodDTO update(ExtractionMethodDTO extractionMethodDTO) {
        log.debug("Request to save ExtractionMethod : {}", extractionMethodDTO);
        ExtractionMethod extractionMethod = extractionMethodMapper.toEntity(extractionMethodDTO);
        extractionMethod = extractionMethodRepository.save(extractionMethod);
        return extractionMethodMapper.toDto(extractionMethod);
    }

    @Override
    public Optional<ExtractionMethodDTO> partialUpdate(ExtractionMethodDTO extractionMethodDTO) {
        log.debug("Request to partially update ExtractionMethod : {}", extractionMethodDTO);

        return extractionMethodRepository
            .findById(extractionMethodDTO.getId())
            .map(existingExtractionMethod -> {
                extractionMethodMapper.partialUpdate(existingExtractionMethod, extractionMethodDTO);

                return existingExtractionMethod;
            })
            .map(extractionMethodRepository::save)
            .map(extractionMethodMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExtractionMethodDTO> findAll() {
        log.debug("Request to get all ExtractionMethods");
        return extractionMethodRepository
            .findAll()
            .stream()
            .map(extractionMethodMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExtractionMethodDTO> findOne(Long id) {
        log.debug("Request to get ExtractionMethod : {}", id);
        return extractionMethodRepository.findById(id).map(extractionMethodMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExtractionMethod : {}", id);
        extractionMethodRepository.deleteById(id);
    }
}
