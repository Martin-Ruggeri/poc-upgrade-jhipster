package com.pae.well.service;

import com.pae.well.service.dto.GasPlantDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pae.well.domain.GasPlant}.
 */
public interface GasPlantService {
    /**
     * Save a gasPlant.
     *
     * @param gasPlantDTO the entity to save.
     * @return the persisted entity.
     */
    GasPlantDTO save(GasPlantDTO gasPlantDTO);

    /**
     * Updates a gasPlant.
     *
     * @param gasPlantDTO the entity to update.
     * @return the persisted entity.
     */
    GasPlantDTO update(GasPlantDTO gasPlantDTO);

    /**
     * Partially updates a gasPlant.
     *
     * @param gasPlantDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GasPlantDTO> partialUpdate(GasPlantDTO gasPlantDTO);

    /**
     * Get all the gasPlants.
     *
     * @return the list of entities.
     */
    List<GasPlantDTO> findAll();

    /**
     * Get the "id" gasPlant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GasPlantDTO> findOne(Long id);

    /**
     * Delete the "id" gasPlant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
