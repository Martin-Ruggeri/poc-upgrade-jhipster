package com.pae.well.repository;

import com.pae.well.domain.AdditionalDataItemDescription;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AdditionalDataItemDescription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdditionalDataItemDescriptionRepository extends JpaRepository<AdditionalDataItemDescription, Long> {}
