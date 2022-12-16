package com.pae.well.service;

import com.pae.well.service.dto.WellDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.pae.well.domain.Well}.
 */
public interface WellService {
    /**
     * Save a well.
     *
     * @param wellDTO the entity to save.
     * @return the persisted entity.
     */
    WellDTO save(WellDTO wellDTO);

    /**
     * Updates a well.
     *
     * @param wellDTO the entity to update.
     * @return the persisted entity.
     */
    WellDTO update(WellDTO wellDTO);

    /**
     * Partially updates a well.
     *
     * @param wellDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WellDTO> partialUpdate(WellDTO wellDTO);

    /**
     * Get all the wells.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WellDTO> findAll(Pageable pageable);

    /**
     * Get the "id" well.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WellDTO> findOne(Long id);

    /**
     * Delete the "id" well.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
