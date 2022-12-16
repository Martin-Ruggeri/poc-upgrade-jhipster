package com.pae.well.repository;

import com.pae.well.domain.AdditionalDataItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AdditionalDataItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdditionalDataItemRepository extends JpaRepository<AdditionalDataItem, Long> {}
