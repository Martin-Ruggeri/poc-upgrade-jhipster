package com.pae.well.service;

import com.pae.well.service.dto.AdditionalDataDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pae.well.domain.AdditionalData}.
 */
public interface AdditionalDataService {
    /**
     * Save a additionalData.
     *
     * @param additionalDataDTO the entity to save.
     * @return the persisted entity.
     */
    AdditionalDataDTO save(AdditionalDataDTO additionalDataDTO);

    /**
     * Updates a additionalData.
     *
     * @param additionalDataDTO the entity to update.
     * @return the persisted entity.
     */
    AdditionalDataDTO update(AdditionalDataDTO additionalDataDTO);

    /**
     * Partially updates a additionalData.
     *
     * @param additionalDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AdditionalDataDTO> partialUpdate(AdditionalDataDTO additionalDataDTO);

    /**
     * Get all the additionalData.
     *
     * @return the list of entities.
     */
    List<AdditionalDataDTO> findAll();

    /**
     * Get the "id" additionalData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdditionalDataDTO> findOne(Long id);

    /**
     * Delete the "id" additionalData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
