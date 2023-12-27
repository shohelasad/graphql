package com.sentryc.graphqlapi.sales;

import com.sentryc.graphqlapi.pagination.dto.PageInput;
import com.sentryc.graphqlapi.sales.domains.SellerInfo;
import com.sentryc.graphqlapi.sales.dto.SellerFilter;
import com.sentryc.graphqlapi.sales.dto.SellerSortBy;
import com.sentryc.graphqlapi.sales.repositories.SellerRepository;
import com.sentryc.graphqlapi.sales.utils.FilterUtil;
import com.sentryc.graphqlapi.sales.utils.SortingUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase
@DataJpaTest
@ActiveProfiles("test")
class SellerRepositoryTest {
    @Autowired
    private SellerRepository sellerRepository;

    @Test
    void testFindAllWithSpecification() {
        String searchByName = "Amazon";
        UUID producerId = UUID.fromString("e7cd8d65-9556-4f94-83db-0d12a1e4c0f0");
        String marketplaceId = "amazon.us";
        List<String> marketplaceIds = List.of(marketplaceId);
        List<String> producerIds = List.of(producerId.toString());
        SellerFilter sellerFilter = new SellerFilter(searchByName, producerIds, marketplaceIds);

        Specification<SellerInfo> specification = FilterUtil.createSpecification(sellerFilter);

        PageInput pageInput = new PageInput(0, 10);
        SellerSortBy sortBy = SellerSortBy.NAME_ASC;

        Pageable pageable = PageRequest.of(pageInput.page(), pageInput.size(), SortingUtil.getSort(sortBy));
        Page<SellerInfo> resultPage = sellerRepository.findAll(specification, pageable);

        assertFalse(resultPage.isEmpty());
        assertEquals(1, resultPage.getTotalElements());
        assertEquals("Amazon Us", resultPage.getContent().get(0).getSellerName());
    }

    @Test
    void testFindAllWithSpecificationWithEmptyFilterSortByNameAsc() {
        SellerFilter sellerFilter = new SellerFilter(null, null, null);

        Specification<SellerInfo> specification = FilterUtil.createSpecification(sellerFilter);

        PageInput pageInput = new PageInput(0, 10);
        SellerSortBy sortBy = SellerSortBy.NAME_ASC;

        Pageable pageable = PageRequest.of(pageInput.page(), pageInput.size(), SortingUtil.getSort(sortBy));
        Page<SellerInfo> resultPage = sellerRepository.findAll(specification, pageable);

        assertFalse(resultPage.isEmpty());
        assertEquals(2, resultPage.getTotalElements());
        assertEquals("Amazon Us", resultPage.getContent().get(0).getSellerName());
        assertEquals("Simply4you", resultPage.getContent().get(1).getSellerName());
    }

    @Test
    void testFindAllWithSpecificationWithEmptyFilterSortByNameDesc() {
        SellerFilter sellerFilter = new SellerFilter(null, null, null);

        Specification<SellerInfo> specification = FilterUtil.createSpecification(sellerFilter);

        PageInput pageInput = new PageInput(0, 10);
        SellerSortBy sortBy = SellerSortBy.NAME_DESC;

        Pageable pageable = PageRequest.of(pageInput.page(), pageInput.size(), SortingUtil.getSort(sortBy));
        Page<SellerInfo> resultPage = sellerRepository.findAll(specification, pageable);

        assertFalse(resultPage.isEmpty());
        assertEquals(2, resultPage.getTotalElements());
        assertEquals("Simply4you", resultPage.getContent().get(0).getSellerName());
        assertEquals("Amazon Us", resultPage.getContent().get(1).getSellerName());
    }

    @Test
    void testFindAllWithSpecificationWithEmptyFilterSortByMarketplaceIdAsc() {
        SellerFilter sellerFilter = new SellerFilter(null, null, null);

        Specification<SellerInfo> specification = FilterUtil.createSpecification(sellerFilter);

        PageInput pageInput = new PageInput(0, 10);
        SellerSortBy sortBy = SellerSortBy.MARKETPLACE_ID_ASC;

        Pageable pageable = PageRequest.of(pageInput.page(), pageInput.size(), SortingUtil.getSort(sortBy));
        Page<SellerInfo> resultPage = sellerRepository.findAll(specification, pageable);

        assertFalse(resultPage.isEmpty());
        assertEquals(2, resultPage.getTotalElements());
        assertEquals("amazon.us", resultPage.getContent().get(0).getMarketplace().getId());
        assertEquals("simply4you.eu", resultPage.getContent().get(1).getMarketplace().getId());
    }

    @Test
    void testFindAllWithSpecificationWithEmptyFilterSortByExternalIdDesc() {
        SellerFilter sellerFilter = new SellerFilter(null, null, null);

        Specification<SellerInfo> specification = FilterUtil.createSpecification(sellerFilter);

        PageInput pageInput = new PageInput(0, 10);
        SellerSortBy sortBy = SellerSortBy.SELLER_INFO_EXTERNAL_ID_DESC;

        Pageable pageable = PageRequest.of(pageInput.page(), pageInput.size(), SortingUtil.getSort(sortBy));
        Page<SellerInfo> resultPage = sellerRepository.findAll(specification, pageable);

        assertFalse(resultPage.isEmpty());
        assertEquals(2, resultPage.getTotalElements());
        assertEquals("external_id_2", resultPage.getContent().get(0).getExternalId());
        assertEquals("external_id_1", resultPage.getContent().get(1).getExternalId());
    }

    @Test
    void testFindAllWithSpecificationWhenNotFound() {
        String searchByName = "simply";
        UUID producerId = UUID.fromString("e7cd8d65-9556-4f94-83db-0d12a1e4c0f0");
        String marketplaceId = "amazon.us";
        List<String> marketplaceIds = List.of(marketplaceId);
        List<String> producerIds = List.of(producerId.toString());
        SellerFilter sellerFilter = new SellerFilter(searchByName, producerIds, marketplaceIds);

        Specification<SellerInfo> specification = FilterUtil.createSpecification(sellerFilter);

        PageInput pageInput = new PageInput(0, 10);
        SellerSortBy sortBy = SellerSortBy.NAME_ASC;

        Pageable pageable = PageRequest.of(pageInput.page(), pageInput.size(), SortingUtil.getSort(sortBy));
        Page<SellerInfo> resultPage = sellerRepository.findAll(specification, pageable);

        assertTrue(resultPage.isEmpty());
    }
}
