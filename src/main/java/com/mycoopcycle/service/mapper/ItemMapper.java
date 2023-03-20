package com.mycoopcycle.service.mapper;

import com.mycoopcycle.domain.CategorieProduit;
import com.mycoopcycle.domain.Item;
import com.mycoopcycle.domain.Produit;
import com.mycoopcycle.service.dto.CategorieProduitDTO;
import com.mycoopcycle.service.dto.ItemDTO;
import com.mycoopcycle.service.dto.ProduitDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Item} and its DTO {@link ItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface ItemMapper extends EntityMapper<ItemDTO, Item> {
    @Mapping(target = "produit", source = "produit", qualifiedByName = "produitId")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "categorieProduitIdSet")
    ItemDTO toDto(Item s);

    @Mapping(target = "removeCategories", ignore = true)
    Item toEntity(ItemDTO itemDTO);

    @Named("produitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProduitDTO toDtoProduitId(Produit produit);

    @Named("categorieProduitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategorieProduitDTO toDtoCategorieProduitId(CategorieProduit categorieProduit);

    @Named("categorieProduitIdSet")
    default Set<CategorieProduitDTO> toDtoCategorieProduitIdSet(Set<CategorieProduit> categorieProduit) {
        return categorieProduit.stream().map(this::toDtoCategorieProduitId).collect(Collectors.toSet());
    }
}
