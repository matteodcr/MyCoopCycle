package com.mycoopcycle.service.mapper;

import com.mycoopcycle.domain.Commercant;
import com.mycoopcycle.domain.Cooperative;
import com.mycoopcycle.domain.Livreur;
import com.mycoopcycle.service.dto.CommercantDTO;
import com.mycoopcycle.service.dto.CooperativeDTO;
import com.mycoopcycle.service.dto.LivreurDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cooperative} and its DTO {@link CooperativeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CooperativeMapper extends EntityMapper<CooperativeDTO, Cooperative> {
    @Mapping(target = "livreurs", source = "livreurs", qualifiedByName = "livreurIdSet")
    @Mapping(target = "commercants", source = "commercants", qualifiedByName = "commercantIdSet")
    CooperativeDTO toDto(Cooperative s);

    @Mapping(target = "removeLivreurs", ignore = true)
    @Mapping(target = "removeCommercants", ignore = true)
    Cooperative toEntity(CooperativeDTO cooperativeDTO);

    @Named("livreurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LivreurDTO toDtoLivreurId(Livreur livreur);

    @Named("livreurIdSet")
    default Set<LivreurDTO> toDtoLivreurIdSet(Set<Livreur> livreur) {
        return livreur.stream().map(this::toDtoLivreurId).collect(Collectors.toSet());
    }

    @Named("commercantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CommercantDTO toDtoCommercantId(Commercant commercant);

    @Named("commercantIdSet")
    default Set<CommercantDTO> toDtoCommercantIdSet(Set<Commercant> commercant) {
        return commercant.stream().map(this::toDtoCommercantId).collect(Collectors.toSet());
    }
}
