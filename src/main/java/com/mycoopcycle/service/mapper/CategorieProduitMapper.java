package com.mycoopcycle.service.mapper;

import com.mycoopcycle.domain.CategorieProduit;
import com.mycoopcycle.service.dto.CategorieProduitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategorieProduit} and its DTO {@link CategorieProduitDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategorieProduitMapper extends EntityMapper<CategorieProduitDTO, CategorieProduit> {}
