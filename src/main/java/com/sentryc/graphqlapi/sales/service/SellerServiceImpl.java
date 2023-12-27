package com.sentryc.graphqlapi.sales.service;

import com.sentryc.graphqlapi.pagination.dto.*;
import com.sentryc.graphqlapi.sales.utils.FilterUtil;
import com.sentryc.graphqlapi.sales.utils.SortingUtil;
import com.sentryc.graphqlapi.sales.domains.SellerInfo;
import com.sentryc.graphqlapi.sales.repositories.SellerRepository;
import com.sentryc.graphqlapi.sales.dto.*;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Transactional
@Service
@Slf4j
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;

    private final CacheManager cacheManager;

    public SellerServiceImpl(SellerRepository sellerRepository, CacheManager cacheManager) {
        this.sellerRepository = sellerRepository;
        this.cacheManager = cacheManager;
    }

    /*@Cacheable(value = "sellers", key = "#filter.hashCode() + #page.hashCode() + #sortBy.hashCode()")
    @Override
    public SellerPageableResponse getSellers(SellerFilter filter, PageInput pageInput, SellerSortBy sortBy) {
        log.info("Preparing specification with seller filter: {}", filter);
        Specification<SellerInfo> specification = FilterUtil.createSpecification(filter);
        Pageable pageable = PageRequest.of(pageInput.page(), pageInput.size(), SortingUtil.getSort(sortBy));
        Page<SellerInfo> sellerInfos = sellerRepository.findAll(specification, pageable);
        log.info("Retrieved seller infos with filter: {}", sellerInfos);
        PageMeta pageMeta = new PageMeta(sellerInfos.getTotalElements(), sellerInfos.getTotalPages(),
                sellerInfos.getNumber(), sellerInfos.getSize());
        List<Seller> sellers = DtoMapper.mapToSellers(sellerInfos.getContent());

        return new SellerPageableResponse(pageMeta, sellers);
    }*/

    @Cacheable(value = "sellers", key = "#filter != null ? #filter.hashCode() : 'null' + '_' + #pageInput != null ? " +
            "#pageInput.hashCode() : 'null' + '_' + #sortBy != null ? #sortBy.hashCode() : 'null'")
    @Override
    public SellerPageableResponse getSellers(SellerFilter filter, PageInput pageInput, SellerSortBy sortBy) {
        log.info("Preparing specification with seller filter: {}", filter);

        // Add null checks for filter, pageInput, and sortBy
        Specification<SellerInfo> specification = FilterUtil.createSpecification(filter);
        Pageable pageable = PageRequest.of(pageInput != null ? pageInput.page() : 0, pageInput != null ? pageInput.size() : 10, SortingUtil.getSort(sortBy));

        Page<SellerInfo> sellerInfos = sellerRepository.findAll(specification, pageable);
        log.info("Retrieved seller infos with filter: {}", sellerInfos);

        PageMeta pageMeta = new PageMeta(sellerInfos.getTotalElements(), sellerInfos.getTotalPages(),
                sellerInfos.getNumber(), sellerInfos.getSize());

        List<Seller> sellers = DtoMapper.mapToSellers(sellerInfos.getContent());

        return new SellerPageableResponse(pageMeta, sellers);
    }

}
