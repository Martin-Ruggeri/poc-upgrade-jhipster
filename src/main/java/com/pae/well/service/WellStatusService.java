package com.pae.well.service;

import com.pae.well.service.dto.WellStatusDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pae.well.domain.WellStatus}.
 */
public interface WellStatusService {
    /**
     * Save a wellStatus.
     *
     * @param wellStatusDTO the entity to save.
     * @return the persisted entity.
     */
    WellStatusDTO save(WellStatusDTO wellStatusDTO);

    /**
     * Updates a wellStatus.
     *
     * @param wellStatusDTO the entity to update.
     * @return the persisted entity.
     */
    WellStatusDTO update(WellStatusDTO wellStatusDTO);

    /**
     * Partially updates a wellStatus.
     *
     * @param wellStatusDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WellStatusDTO> partialUpdate(WellStatusDTO wellStatusDTO);

    /**
     * Get all the wellStatuses.
     *
     * @return the list of entities.
     */
    List<WellStatusDTO> findAll();

    /**
     * Get the "id" wellStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WellStatusDTO> findOne(Long id);

    /**
     * Delete the "id" wellStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
