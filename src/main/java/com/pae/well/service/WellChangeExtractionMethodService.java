package com.pae.well.service;

import com.pae.well.service.dto.WellChangeExtractionMethodDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.pae.well.domain.WellChangeExtractionMethod}.
 */
public interface WellChangeExtractionMethodService {
    /**
     * Save a wellChangeExtractionMethod.
     *
     * @param wellChangeExtractionMethodDTO the entity to save.
     * @return the persisted entity.
     */
    WellChangeExtractionMethodDTO save(WellChangeExtractionMethodDTO wellChangeExtractionMethodDTO);

    /**
     * Updates a wellChangeExtractionMethod.
     *
     * @param wellChangeExtractionMethodDTO the entity to update.
     * @return the persisted entity.
     */
    WellChangeExtractionMethodDTO update(WellChangeExtractionMethodDTO wellChangeExtractionMethodDTO);

    /**
     * Partially updates a wellChangeExtractionMethod.
     *
     * @param wellChangeExtractionMethodDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WellChangeExtractionMethodDTO> partialUpdate(WellChangeExtractionMethodDTO wellChangeExtractionMethodDTO);

    /**
     * Get all the wellChangeExtractionMethods.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WellChangeExtractionMethodDTO> findAll(Pageable pageable);

    /**
     * Get the "id" wellChangeExtractionMethod.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WellChangeExtractionMethodDTO> findOne(Long id);

    /**
     * Delete the "id" wellChangeExtractionMethod.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
