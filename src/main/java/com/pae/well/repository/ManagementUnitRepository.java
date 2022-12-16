package com.pae.well.repository;

import com.pae.well.domain.ManagementUnit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ManagementUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ManagementUnitRepository extends JpaRepository<ManagementUnit, Long> {}
