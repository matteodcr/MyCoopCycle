package com.mycoopcycle.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycoopcycle.domain.Commande} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommandeDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime dateLivraison;

    private ClientDTO client;

    private LivreurDTO livreur;

    private CommercantDTO commercant;

    private ItemDTO produit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(ZonedDateTime dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public LivreurDTO getLivreur() {
        return livreur;
    }

    public void setLivreur(LivreurDTO livreur) {
        this.livreur = livreur;
    }

    public CommercantDTO getCommercant() {
        return commercant;
    }

    public void setCommercant(CommercantDTO commercant) {
        this.commercant = commercant;
    }

    public ItemDTO getProduit() {
        return produit;
    }

    public void setProduit(ItemDTO produit) {
        this.produit = produit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommandeDTO)) {
            return false;
        }

        CommandeDTO commandeDTO = (CommandeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, commandeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommandeDTO{" +
            "id=" + getId() +
            ", dateLivraison='" + getDateLivraison() + "'" +
            ", client=" + getClient() +
            ", livreur=" + getLivreur() +
            ", commercant=" + getCommercant() +
            ", produit=" + getProduit() +
            "}";
    }
}
