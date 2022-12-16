package com.pae.well.service;

import com.pae.well.service.dto.AdditionalDataItemDescriptionDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pae.well.domain.AdditionalDataItemDescription}.
 */
public interface AdditionalDataItemDescriptionService {
    /**
     * Save a additionalDataItemDescription.
     *
     * @param additionalDataItemDescriptionDTO the entity to save.
     * @return the persisted entity.
     */
    AdditionalDataItemDescriptionDTO save(AdditionalDataItemDescriptionDTO additionalDataItemDescriptionDTO);

    /**
     * Updates a additionalDataItemDescription.
     *
     * @param additionalDataItemDescriptionDTO the entity to update.
     * @return the persisted entity.
     */
    AdditionalDataItemDescriptionDTO update(AdditionalDataItemDescriptionDTO additionalDataItemDescriptionDTO);

    /**
     * Partially updates a additionalDataItemDescription.
     *
     * @param additionalDataItemDescriptionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AdditionalDataItemDescriptionDTO> partialUpdate(AdditionalDataItemDescriptionDTO additionalDataItemDescriptionDTO);

    /**
     * Get all the additionalDataItemDescriptions.
     *
     * @return the list of entities.
     */
    List<AdditionalDataItemDescriptionDTO> findAll();

    /**
     * Get the "id" additionalDataItemDescription.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdditionalDataItemDescriptionDTO> findOne(Long id);

    /**
     * Delete the "id" additionalDataItemDescription.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
