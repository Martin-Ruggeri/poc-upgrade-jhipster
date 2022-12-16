package com.pae.well.service;

import com.pae.well.service.dto.ManagementUnitDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pae.well.domain.ManagementUnit}.
 */
public interface ManagementUnitService {
    /**
     * Save a managementUnit.
     *
     * @param managementUnitDTO the entity to save.
     * @return the persisted entity.
     */
    ManagementUnitDTO save(ManagementUnitDTO managementUnitDTO);

    /**
     * Updates a managementUnit.
     *
     * @param managementUnitDTO the entity to update.
     * @return the persisted entity.
     */
    ManagementUnitDTO update(ManagementUnitDTO managementUnitDTO);

    /**
     * Partially updates a managementUnit.
     *
     * @param managementUnitDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ManagementUnitDTO> partialUpdate(ManagementUnitDTO managementUnitDTO);

    /**
     * Get all the managementUnits.
     *
     * @return the list of entities.
     */
    List<ManagementUnitDTO> findAll();

    /**
     * Get the "id" managementUnit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ManagementUnitDTO> findOne(Long id);

    /**
     * Delete the "id" managementUnit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
