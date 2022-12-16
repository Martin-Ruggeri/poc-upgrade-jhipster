package com.pae.well.service;

import com.pae.well.service.dto.PetroleumPlantDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pae.well.domain.PetroleumPlant}.
 */
public interface PetroleumPlantService {
    /**
     * Save a petroleumPlant.
     *
     * @param petroleumPlantDTO the entity to save.
     * @return the persisted entity.
     */
    PetroleumPlantDTO save(PetroleumPlantDTO petroleumPlantDTO);

    /**
     * Updates a petroleumPlant.
     *
     * @param petroleumPlantDTO the entity to update.
     * @return the persisted entity.
     */
    PetroleumPlantDTO update(PetroleumPlantDTO petroleumPlantDTO);

    /**
     * Partially updates a petroleumPlant.
     *
     * @param petroleumPlantDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PetroleumPlantDTO> partialUpdate(PetroleumPlantDTO petroleumPlantDTO);

    /**
     * Get all the petroleumPlants.
     *
     * @return the list of entities.
     */
    List<PetroleumPlantDTO> findAll();

    /**
     * Get the "id" petroleumPlant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PetroleumPlantDTO> findOne(Long id);

    /**
     * Delete the "id" petroleumPlant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
