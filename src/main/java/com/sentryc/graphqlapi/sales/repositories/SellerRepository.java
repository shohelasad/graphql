package com.sentryc.graphqlapi.sales.repositories;

import com.sentryc.graphqlapi.sales.domains.SellerInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface SellerRepository extends JpaRepository<SellerInfo, UUID>,
        JpaSpecificationExecutor<SellerInfo> {

    Page<SellerInfo> findAll(Specification<SellerInfo> specification, Pageable pageable);
}
