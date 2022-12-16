package com.pae.well.service;

import com.pae.well.service.dto.ExtractionMethodDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pae.well.domain.ExtractionMethod}.
 */
public interface ExtractionMethodService {
    /**
     * Save a extractionMethod.
     *
     * @param extractionMethodDTO the entity to save.
     * @return the persisted entity.
     */
    ExtractionMethodDTO save(ExtractionMethodDTO extractionMethodDTO);

    /**
     * Updates a extractionMethod.
     *
     * @param extractionMethodDTO the entity to update.
     * @return the persisted entity.
     */
    ExtractionMethodDTO update(ExtractionMethodDTO extractionMethodDTO);

    /**
     * Partially updates a extractionMethod.
     *
     * @param extractionMethodDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ExtractionMethodDTO> partialUpdate(ExtractionMethodDTO extractionMethodDTO);

    /**
     * Get all the extractionMethods.
     *
     * @return the list of entities.
     */
    List<ExtractionMethodDTO> findAll();

    /**
     * Get the "id" extractionMethod.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExtractionMethodDTO> findOne(Long id);

    /**
     * Delete the "id" extractionMethod.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
