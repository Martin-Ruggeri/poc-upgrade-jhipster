package com.pae.well.repository;

import com.pae.well.domain.SaltWaterInjectionPlant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SaltWaterInjectionPlant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaltWaterInjectionPlantRepository extends JpaRepository<SaltWaterInjectionPlant, Long> {}
