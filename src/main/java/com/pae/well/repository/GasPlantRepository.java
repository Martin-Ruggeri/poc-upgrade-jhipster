package com.pae.well.repository;

import com.pae.well.domain.GasPlant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GasPlant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GasPlantRepository extends JpaRepository<GasPlant, Long> {}
