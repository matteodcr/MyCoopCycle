package com.mycoopcycle.repository;

import com.mycoopcycle.domain.Cooperative;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CooperativeRepositoryWithBagRelationships {
    Optional<Cooperative> fetchBagRelationships(Optional<Cooperative> cooperative);

    List<Cooperative> fetchBagRelationships(List<Cooperative> cooperatives);

    Page<Cooperative> fetchBagRelationships(Page<Cooperative> cooperatives);
}
