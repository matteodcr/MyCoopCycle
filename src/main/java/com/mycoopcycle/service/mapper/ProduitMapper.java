package com.mycoopcycle.service.mapper;

import com.mycoopcycle.domain.Produit;
import com.mycoopcycle.service.dto.ProduitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Produit} and its DTO {@link ProduitDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProduitMapper extends EntityMapper<ProduitDTO, Produit> {}
