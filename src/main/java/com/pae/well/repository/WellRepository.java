package com.pae.well.repository;

import com.pae.well.domain.Well;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Well entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WellRepository extends JpaRepository<Well, Long> {}
