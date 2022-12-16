package com.pae.well.repository;

import com.pae.well.domain.WellChangeExtractionMethod;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WellChangeExtractionMethod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WellChangeExtractionMethodRepository extends JpaRepository<WellChangeExtractionMethod, Long> {}
