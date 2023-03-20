package com.mycoopcycle.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Commande.
 */
@Entity
@Table(name = "commande")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Commande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date_livraison", nullable = false)
    private ZonedDateTime dateLivraison;

    @OneToOne
    @JoinColumn(unique = true)
    private Client client;

    @JsonIgnoreProperties(value = { "cooperatives" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Livreur livreur;

    @JsonIgnoreProperties(value = { "cooperatives" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Commercant commercant;

    @ManyToOne
    @JsonIgnoreProperties(value = { "produit", "categories" }, allowSetters = true)
    private Item produit;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Commande id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateLivraison() {
        return this.dateLivraison;
    }

    public Commande dateLivraison(ZonedDateTime dateLivraison) {
        this.setDateLivraison(dateLivraison);
        return this;
    }

    public void setDateLivraison(ZonedDateTime dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Commande client(Client client) {
        this.setClient(client);
        return this;
    }

    public Livreur getLivreur() {
        return this.livreur;
    }

    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
    }

    public Commande livreur(Livreur livreur) {
        this.setLivreur(livreur);
        return this;
    }

    public Commercant getCommercant() {
        return this.commercant;
    }

    public void setCommercant(Commercant commercant) {
        this.commercant = commercant;
    }

    public Commande commercant(Commercant commercant) {
        this.setCommercant(commercant);
        return this;
    }

    public Item getProduit() {
        return this.produit;
    }

    public void setProduit(Item item) {
        this.produit = item;
    }

    public Commande produit(Item item) {
        this.setProduit(item);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commande)) {
            return false;
        }
        return id != null && id.equals(((Commande) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Commande{" +
            "id=" + getId() +
            ", dateLivraison='" + getDateLivraison() + "'" +
            "}";
    }
}
