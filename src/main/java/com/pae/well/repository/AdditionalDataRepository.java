package com.pae.well.repository;

import com.pae.well.domain.AdditionalData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AdditionalData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdditionalDataRepository extends JpaRepository<AdditionalData, Long> {}
