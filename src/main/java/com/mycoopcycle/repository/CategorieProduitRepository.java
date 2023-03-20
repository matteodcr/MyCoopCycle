package com.mycoopcycle.repository;

import com.mycoopcycle.domain.CategorieProduit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CategorieProduit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorieProduitRepository extends JpaRepository<CategorieProduit, Long> {}
