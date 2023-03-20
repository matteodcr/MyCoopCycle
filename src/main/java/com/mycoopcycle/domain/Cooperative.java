package com.mycoopcycle.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cooperative.
 */
@Entity
@Table(name = "cooperative")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cooperative implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
        name = "rel_cooperative__livreurs",
        joinColumns = @JoinColumn(name = "cooperative_id"),
        inverseJoinColumns = @JoinColumn(name = "livreurs_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cooperatives" }, allowSetters = true)
    private Set<Livreur> livreurs = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_cooperative__commercants",
        joinColumns = @JoinColumn(name = "cooperative_id"),
        inverseJoinColumns = @JoinColumn(name = "commercants_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cooperatives" }, allowSetters = true)
    private Set<Commercant> commercants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cooperative id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Cooperative name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Livreur> getLivreurs() {
        return this.livreurs;
    }

    public void setLivreurs(Set<Livreur> livreurs) {
        this.livreurs = livreurs;
    }

    public Cooperative livreurs(Set<Livreur> livreurs) {
        this.setLivreurs(livreurs);
        return this;
    }

    public Cooperative addLivreurs(Livreur livreur) {
        this.livreurs.add(livreur);
        livreur.getCooperatives().add(this);
        return this;
    }

    public Cooperative removeLivreurs(Livreur livreur) {
        this.livreurs.remove(livreur);
        livreur.getCooperatives().remove(this);
        return this;
    }

    public Set<Commercant> getCommercants() {
        return this.commercants;
    }

    public void setCommercants(Set<Commercant> commercants) {
        this.commercants = commercants;
    }

    public Cooperative commercants(Set<Commercant> commercants) {
        this.setCommercants(commercants);
        return this;
    }

    public Cooperative addCommercants(Commercant commercant) {
        this.commercants.add(commercant);
        commercant.getCooperatives().add(this);
        return this;
    }

    public Cooperative removeCommercants(Commercant commercant) {
        this.commercants.remove(commercant);
        commercant.getCooperatives().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cooperative)) {
            return false;
        }
        return id != null && id.equals(((Cooperative) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cooperative{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
