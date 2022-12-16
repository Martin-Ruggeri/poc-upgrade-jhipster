package com.pae.well.service;

import com.pae.well.service.dto.RigDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pae.well.domain.Rig}.
 */
public interface RigService {
    /**
     * Save a rig.
     *
     * @param rigDTO the entity to save.
     * @return the persisted entity.
     */
    RigDTO save(RigDTO rigDTO);

    /**
     * Updates a rig.
     *
     * @param rigDTO the entity to update.
     * @return the persisted entity.
     */
    RigDTO update(RigDTO rigDTO);

    /**
     * Partially updates a rig.
     *
     * @param rigDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RigDTO> partialUpdate(RigDTO rigDTO);

    /**
     * Get all the rigs.
     *
     * @return the list of entities.
     */
    List<RigDTO> findAll();

    /**
     * Get the "id" rig.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RigDTO> findOne(Long id);

    /**
     * Delete the "id" rig.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
