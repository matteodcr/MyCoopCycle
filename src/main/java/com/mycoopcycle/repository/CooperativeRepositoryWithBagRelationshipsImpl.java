package com.mycoopcycle.repository;

import com.mycoopcycle.domain.Cooperative;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class CooperativeRepositoryWithBagRelationshipsImpl implements CooperativeRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Cooperative> fetchBagRelationships(Optional<Cooperative> cooperative) {
        return cooperative.map(this::fetchLivreurs).map(this::fetchCommercants);
    }

    @Override
    public Page<Cooperative> fetchBagRelationships(Page<Cooperative> cooperatives) {
        return new PageImpl<>(
            fetchBagRelationships(cooperatives.getContent()),
            cooperatives.getPageable(),
            cooperatives.getTotalElements()
        );
    }

    @Override
    public List<Cooperative> fetchBagRelationships(List<Cooperative> cooperatives) {
        return Optional.of(cooperatives).map(this::fetchLivreurs).map(this::fetchCommercants).orElse(Collections.emptyList());
    }

    Cooperative fetchLivreurs(Cooperative result) {
        return entityManager
            .createQuery(
                "select cooperative from Cooperative cooperative left join fetch cooperative.livreurs where cooperative is :cooperative",
                Cooperative.class
            )
            .setParameter("cooperative", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Cooperative> fetchLivreurs(List<Cooperative> cooperatives) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, cooperatives.size()).forEach(index -> order.put(cooperatives.get(index).getId(), index));
        List<Cooperative> result = entityManager
            .createQuery(
                "select distinct cooperative from Cooperative cooperative left join fetch cooperative.livreurs where cooperative in :cooperatives",
                Cooperative.class
            )
            .setParameter("cooperatives", cooperatives)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Cooperative fetchCommercants(Cooperative result) {
        return entityManager
            .createQuery(
                "select cooperative from Cooperative cooperative left join fetch cooperative.commercants where cooperative is :cooperative",
                Cooperative.class
            )
            .setParameter("cooperative", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Cooperative> fetchCommercants(List<Cooperative> cooperatives) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, cooperatives.size()).forEach(index -> order.put(cooperatives.get(index).getId(), index));
        List<Cooperative> result = entityManager
            .createQuery(
                "select distinct cooperative from Cooperative cooperative left join fetch cooperative.commercants where cooperative in :cooperatives",
                Cooperative.class
            )
            .setParameter("cooperatives", cooperatives)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
