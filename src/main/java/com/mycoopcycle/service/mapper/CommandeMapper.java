package com.mycoopcycle.service.mapper;

import com.mycoopcycle.domain.Client;
import com.mycoopcycle.domain.Commande;
import com.mycoopcycle.domain.Commercant;
import com.mycoopcycle.domain.Item;
import com.mycoopcycle.domain.Livreur;
import com.mycoopcycle.service.dto.ClientDTO;
import com.mycoopcycle.service.dto.CommandeDTO;
import com.mycoopcycle.service.dto.CommercantDTO;
import com.mycoopcycle.service.dto.ItemDTO;
import com.mycoopcycle.service.dto.LivreurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Commande} and its DTO {@link CommandeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommandeMapper extends EntityMapper<CommandeDTO, Commande> {
    @Mapping(target = "client", source = "client", qualifiedByName = "clientId")
    @Mapping(target = "livreur", source = "livreur", qualifiedByName = "livreurId")
    @Mapping(target = "commercant", source = "commercant", qualifiedByName = "commercantId")
    @Mapping(target = "produit", source = "produit", qualifiedByName = "itemId")
    CommandeDTO toDto(Commande s);

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoClientId(Client client);

    @Named("livreurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LivreurDTO toDtoLivreurId(Livreur livreur);

    @Named("commercantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CommercantDTO toDtoCommercantId(Commercant commercant);

    @Named("itemId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ItemDTO toDtoItemId(Item item);
}
