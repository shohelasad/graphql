package com.sentryc.graphqlapi.sales.utils;

import com.sentryc.graphqlapi.sales.domains.SellerInfo;
import com.sentryc.graphqlapi.sales.dto.SellerFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FilterUtil {
    private static final String SELLER_NAME_FIELD = "sellerName";
    private static final String SELLER_PRODUCERS_FIELD = "sellerProducers";
    private static final String PRODUCER_FIELD = "producer";
    private static final String PRODUCER_ID_FIELD = "id";
    private static final String MARKETPLACE_FIELD = "marketplace";
    private static final String MARKETPLACE_ID_FIELD = "id";

    public static Specification<SellerInfo> createSpecification(SellerFilter filter) {
        log.info("Preparing specification with seller filter: {}", filter);
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            addSearchByNamePredicate(predicates, root, criteriaBuilder, filter);
            addProducerIdsPredicate(predicates, root, criteriaBuilder, filter);
            addMarketplaceIdsPredicate(predicates, root, criteriaBuilder, filter);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
    private static void addSearchByNamePredicate(List<Predicate> predicates, Root<SellerInfo> root, CriteriaBuilder criteriaBuilder, SellerFilter filter) {
        if (filter.searchByName() != null && !filter.searchByName().isEmpty()) {
            try {
                predicates.add(criteriaBuilder.like(root.get(SELLER_NAME_FIELD), "%" + filter.searchByName() + "%"));
            } catch(IllegalArgumentException e) {
                throw new RuntimeException("Error processing seller name", e);
            }
        }
    }

    private static void addProducerIdsPredicate(List<Predicate> predicates, Root<SellerInfo> root, CriteriaBuilder criteriaBuilder, SellerFilter filter) {
        if (filter.producerIds() != null && !filter.producerIds().isEmpty()) {
            try {
                predicates.add(root.join(SELLER_PRODUCERS_FIELD).join(PRODUCER_FIELD).get(PRODUCER_ID_FIELD).as(String.class).in(filter.producerIds()));
            } catch(IllegalArgumentException e) {
                throw new RuntimeException("Error processing producer Ids", e);
            }
        }
    }

    private static void addMarketplaceIdsPredicate(List<Predicate> predicates, Root<SellerInfo> root, CriteriaBuilder criteriaBuilder, SellerFilter filter) {
        if (filter.marketplaceIds() != null && !filter.marketplaceIds().isEmpty()) {
            try {
                predicates.add(root.get(MARKETPLACE_FIELD).get(MARKETPLACE_ID_FIELD).in(filter.marketplaceIds()));
            } catch(IllegalArgumentException e) {
                throw new RuntimeException("Error processing marketplace Ids", e);
            }
        }
    }
}
