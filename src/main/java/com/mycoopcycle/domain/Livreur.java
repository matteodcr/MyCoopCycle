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
 * A Livreur.
 */
@Entity
@Table(name = "livreur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Livreur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 10)
    @Column(name = "name", length = 10, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "livreurs")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "livreurs", "commercants" }, allowSetters = true)
    private Set<Cooperative> cooperatives = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Livreur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Livreur name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Cooperative> getCooperatives() {
        return this.cooperatives;
    }

    public void setCooperatives(Set<Cooperative> cooperatives) {
        if (this.cooperatives != null) {
            this.cooperatives.forEach(i -> i.removeLivreurs(this));
        }
        if (cooperatives != null) {
            cooperatives.forEach(i -> i.addLivreurs(this));
        }
        this.cooperatives = cooperatives;
    }

    public Livreur cooperatives(Set<Cooperative> cooperatives) {
        this.setCooperatives(cooperatives);
        return this;
    }

    public Livreur addCooperatives(Cooperative cooperative) {
        this.cooperatives.add(cooperative);
        cooperative.getLivreurs().add(this);
        return this;
    }

    public Livreur removeCooperatives(Cooperative cooperative) {
        this.cooperatives.remove(cooperative);
        cooperative.getLivreurs().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Livreur)) {
            return false;
        }
        return id != null && id.equals(((Livreur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Livreur{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
