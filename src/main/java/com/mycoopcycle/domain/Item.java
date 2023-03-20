package com.mycoopcycle.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Item.
 */
@Entity
@Table(name = "item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "prix", precision = 21, scale = 2, nullable = false)
    private BigDecimal prix;

    @ManyToOne
    private Produit produit;

    @ManyToMany
    @JoinTable(
        name = "rel_item__categories",
        joinColumns = @JoinColumn(name = "item_id"),
        inverseJoinColumns = @JoinColumn(name = "categories_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "items" }, allowSetters = true)
    private Set<CategorieProduit> categories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Item id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrix() {
        return this.prix;
    }

    public Item prix(BigDecimal prix) {
        this.setPrix(prix);
        return this;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public Produit getProduit() {
        return this.produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Item produit(Produit produit) {
        this.setProduit(produit);
        return this;
    }

    public Set<CategorieProduit> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<CategorieProduit> categorieProduits) {
        this.categories = categorieProduits;
    }

    public Item categories(Set<CategorieProduit> categorieProduits) {
        this.setCategories(categorieProduits);
        return this;
    }

    public Item addCategories(CategorieProduit categorieProduit) {
        this.categories.add(categorieProduit);
        categorieProduit.getItems().add(this);
        return this;
    }

    public Item removeCategories(CategorieProduit categorieProduit) {
        this.categories.remove(categorieProduit);
        categorieProduit.getItems().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        return id != null && id.equals(((Item) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Item{" +
            "id=" + getId() +
            ", prix=" + getPrix() +
            "}";
    }
}
