package com.mycoopcycle.repository;

import com.mycoopcycle.domain.Commercant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Commercant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommercantRepository extends JpaRepository<Commercant, Long> {}
