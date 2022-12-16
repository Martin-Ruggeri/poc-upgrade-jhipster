package com.pae.well.repository;

import com.pae.well.domain.ExtractionMethod;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ExtractionMethod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExtractionMethodRepository extends JpaRepository<ExtractionMethod, Long> {}
