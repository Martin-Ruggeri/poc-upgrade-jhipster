package com.pae.well.service;

import com.pae.well.service.dto.BatteryDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.pae.well.domain.Battery}.
 */
public interface BatteryService {
    /**
     * Save a battery.
     *
     * @param batteryDTO the entity to save.
     * @return the persisted entity.
     */
    BatteryDTO save(BatteryDTO batteryDTO);

    /**
     * Updates a battery.
     *
     * @param batteryDTO the entity to update.
     * @return the persisted entity.
     */
    BatteryDTO update(BatteryDTO batteryDTO);

    /**
     * Partially updates a battery.
     *
     * @param batteryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BatteryDTO> partialUpdate(BatteryDTO batteryDTO);

    /**
     * Get all the batteries.
     *
     * @return the list of entities.
     */
    List<BatteryDTO> findAll();

    /**
     * Get the "id" battery.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BatteryDTO> findOne(Long id);

    /**
     * Delete the "id" battery.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
