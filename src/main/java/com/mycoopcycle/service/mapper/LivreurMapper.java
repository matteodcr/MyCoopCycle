package com.mycoopcycle.service.mapper;

import com.mycoopcycle.domain.Livreur;
import com.mycoopcycle.service.dto.LivreurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Livreur} and its DTO {@link LivreurDTO}.
 */
@Mapper(componentModel = "spring")
public interface LivreurMapper extends EntityMapper<LivreurDTO, Livreur> {}
