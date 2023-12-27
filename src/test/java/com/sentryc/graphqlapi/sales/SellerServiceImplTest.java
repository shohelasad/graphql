package com.sentryc.graphqlapi.sales;

import com.sentryc.graphqlapi.markets.domains.Marketplace;
import com.sentryc.graphqlapi.pagination.dto.PageInput;
import com.sentryc.graphqlapi.products.domains.Producer;
import com.sentryc.graphqlapi.sales.domains.SellerInfo;
import com.sentryc.graphqlapi.sales.domains.SellerProducer;
import com.sentryc.graphqlapi.sales.dto.SellerFilter;
import com.sentryc.graphqlapi.sales.dto.SellerPageableResponse;
import com.sentryc.graphqlapi.sales.dto.SellerSortBy;
import com.sentryc.graphqlapi.sales.dto.SellerState;
import com.sentryc.graphqlapi.sales.repositories.SellerRepository;
import com.sentryc.graphqlapi.sales.service.SellerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SellerServiceImplTest {
    @Mock
    private SellerRepository sellerRepository;
    @InjectMocks
    private SellerServiceImpl sellerService;

    private UUID sellerId = UUID.fromString("e7cd8d65-9556-4f94-83db-0d12a1e4c0f0");
    private UUID producerId = UUID.fromString("e7cd8d65-9556-4f94-83db-0d12a1e4c0f0");
    private String marketplaceId = "amazon.us";

    @Test
    void getSellersReturnsCorrectResponse() {
        String searchByName = "Amazon";
        List<String> marketplaceIds = List.of(marketplaceId);
        List<String> producerIds = List.of(producerId.toString());
        SellerFilter filter = new SellerFilter(searchByName, producerIds, marketplaceIds);
        PageInput pageInput = new PageInput(1, 10);
        SellerSortBy sortBy = SellerSortBy.NAME_ASC;

        List<SellerInfo> sellerInfoList = new ArrayList<>();
        SellerInfo sellerInfo = createSample();
        sellerInfoList.add(sellerInfo);
        Page<SellerInfo> sellerInfoPage = new PageImpl<>(sellerInfoList);

        when(sellerRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(sellerInfoPage);

        SellerPageableResponse response = sellerService.getSellers(filter, pageInput, sortBy);

        assertEquals(sellerInfoList.size(), response.data().size());
        assertEquals(sellerInfoPage.getTotalElements(), response.meta().totalElements());
        assertEquals(sellerInfoPage.getTotalPages(), response.meta().totalPages());
        assertEquals(sellerInfoPage.getNumber(), response.meta().pageNumber());
        assertEquals(sellerInfoPage.getSize(), response.meta().pageSize());
    }

    @Test
    void getSellersReturnEmptyWhenNotFound() {
        String searchByName = "Amazon1";
        UUID producerId = UUID.fromString("e7cd8d65-9556-4f94-83db-0d12a1e4c0f0");
        String marketplaceId = "amazon.us";
        List<String> marketplaceIds = List.of(marketplaceId);
        List<String> producerIds = List.of(producerId.toString());
        SellerFilter filter = new SellerFilter(searchByName, producerIds, marketplaceIds);
        PageInput pageInput = new PageInput(1, 10);
        SellerSortBy sortBy = SellerSortBy.NAME_ASC;

        List<SellerInfo> sellerInfoList = new ArrayList<>();
        Page<SellerInfo> sellerInfoPage = new PageImpl<>(sellerInfoList);

        when(sellerRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(sellerInfoPage);

        SellerPageableResponse response = sellerService.getSellers(filter, pageInput, sortBy);

        assertEquals(sellerInfoList.size(), response.data().size());
        assertEquals(sellerInfoPage.getTotalElements(), response.meta().totalElements());
        assertEquals(sellerInfoPage.getTotalPages(), response.meta().totalPages());
        assertEquals(sellerInfoPage.getNumber(), response.meta().pageNumber());
        assertEquals(sellerInfoPage.getSize(), response.meta().pageSize());
    }

    private SellerInfo createSample() {
        UUID id = UUID.fromString("c0379b89-4604-4970-854b-4646d1e7f384");
        Marketplace marketplace = new Marketplace(marketplaceId, "Amazon US market");

        String producerName = "Nike";
        Producer producer = new Producer(producerId, producerName, LocalDateTime.now());
        SellerProducer sellerProducer = new SellerProducer();
        sellerProducer.setId(id);
        sellerProducer.setProducer(producer);
        sellerProducer.setState(SellerState.GREYLIST);

        String sellerName = "Amazon";
        String url = "https://example.com/sellers/";
        String externalId = "externalId_1";
        String country = "USA";
        SellerInfo sellerInfo = new SellerInfo(sellerId, sellerName, externalId, country, url, marketplace, List.of(sellerProducer));
        sellerProducer.setSellerInfo(sellerInfo);

        return sellerInfo;
    }
}
