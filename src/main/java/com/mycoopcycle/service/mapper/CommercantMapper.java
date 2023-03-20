package com.mycoopcycle.service.mapper;

import com.mycoopcycle.domain.Commercant;
import com.mycoopcycle.service.dto.CommercantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Commercant} and its DTO {@link CommercantDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommercantMapper extends EntityMapper<CommercantDTO, Commercant> {}
