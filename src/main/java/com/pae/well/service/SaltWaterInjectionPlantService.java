package com.pae.well.service;

import com.pae.well.service.dto.SaltWaterInjectionPlantDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pae.well.domain.SaltWaterInjectionPlant}.
 */
public interface SaltWaterInjectionPlantService {
    /**
     * Save a saltWaterInjectionPlant.
     *
     * @param saltWaterInjectionPlantDTO the entity to save.
     * @return the persisted entity.
     */
    SaltWaterInjectionPlantDTO save(SaltWaterInjectionPlantDTO saltWaterInjectionPlantDTO);

    /**
     * Updates a saltWaterInjectionPlant.
     *
     * @param saltWaterInjectionPlantDTO the entity to update.
     * @return the persisted entity.
     */
    SaltWaterInjectionPlantDTO update(SaltWaterInjectionPlantDTO saltWaterInjectionPlantDTO);

    /**
     * Partially updates a saltWaterInjectionPlant.
     *
     * @param saltWaterInjectionPlantDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SaltWaterInjectionPlantDTO> partialUpdate(SaltWaterInjectionPlantDTO saltWaterInjectionPlantDTO);

    /**
     * Get all the saltWaterInjectionPlants.
     *
     * @return the list of entities.
     */
    List<SaltWaterInjectionPlantDTO> findAll();

    /**
     * Get the "id" saltWaterInjectionPlant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SaltWaterInjectionPlantDTO> findOne(Long id);

    /**
     * Delete the "id" saltWaterInjectionPlant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
