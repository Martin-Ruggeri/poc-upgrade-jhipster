package com.pae.well.repository;

import com.pae.well.domain.WellStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the WellStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WellStatusRepository extends JpaRepository<WellStatus, Long> {}
