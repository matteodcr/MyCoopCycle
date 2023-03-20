package com.mycoopcycle.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycoopcycle.domain.Commercant} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommercantDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 20)
    private String name;

    @Size(min = 5, max = 20)
    private String location;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommercantDTO)) {
            return false;
        }

        CommercantDTO commercantDTO = (CommercantDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, commercantDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommercantDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", location='" + getLocation() + "'" +
            "}";
    }
}
