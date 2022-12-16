package com.pae.well.repository;

import com.pae.well.domain.Battery;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Battery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BatteryRepository extends JpaRepository<Battery, Long> {}
