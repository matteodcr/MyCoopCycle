package com.mycoopcycle.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycoopcycle.domain.Cooperative} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CooperativeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    private Set<LivreurDTO> livreurs = new HashSet<>();

    private Set<CommercantDTO> commercants = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<LivreurDTO> getLivreurs() {
        return livreurs;
    }

    public void setLivreurs(Set<LivreurDTO> livreurs) {
        this.livreurs = livreurs;
    }

    public Set<CommercantDTO> getCommercants() {
        return commercants;
    }

    public void setCommercants(Set<CommercantDTO> commercants) {
        this.commercants = commercants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CooperativeDTO)) {
            return false;
        }

        CooperativeDTO cooperativeDTO = (CooperativeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cooperativeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CooperativeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", livreurs=" + getLivreurs() +
            ", commercants=" + getCommercants() +
            "}";
    }
}
