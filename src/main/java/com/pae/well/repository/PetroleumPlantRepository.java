package com.pae.well.repository;

import com.pae.well.domain.PetroleumPlant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PetroleumPlant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PetroleumPlantRepository extends JpaRepository<PetroleumPlant, Long> {}
