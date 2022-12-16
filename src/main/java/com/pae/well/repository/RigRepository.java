package com.pae.well.repository;

import com.pae.well.domain.Rig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Rig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RigRepository extends JpaRepository<Rig, Long> {}
