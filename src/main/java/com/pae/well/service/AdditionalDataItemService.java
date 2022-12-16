package com.pae.well.service;

import com.pae.well.service.dto.AdditionalDataItemDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pae.well.domain.AdditionalDataItem}.
 */
public interface AdditionalDataItemService {
    /**
     * Save a additionalDataItem.
     *
     * @param additionalDataItemDTO the entity to save.
     * @return the persisted entity.
     */
    AdditionalDataItemDTO save(AdditionalDataItemDTO additionalDataItemDTO);

    /**
     * Updates a additionalDataItem.
     *
     * @param additionalDataItemDTO the entity to update.
     * @return the persisted entity.
     */
    AdditionalDataItemDTO update(AdditionalDataItemDTO additionalDataItemDTO);

    /**
     * Partially updates a additionalDataItem.
     *
     * @param additionalDataItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AdditionalDataItemDTO> partialUpdate(AdditionalDataItemDTO additionalDataItemDTO);

    /**
     * Get all the additionalDataItems.
     *
     * @return the list of entities.
     */
    List<AdditionalDataItemDTO> findAll();

    /**
     * Get the "id" additionalDataItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdditionalDataItemDTO> findOne(Long id);

    /**
     * Delete the "id" additionalDataItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
