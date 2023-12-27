package com.sentryc.graphqlapi.sales.resolver;

import com.sentryc.graphqlapi.pagination.dto.PageInput;
import com.sentryc.graphqlapi.sales.dto.SellerFilter;
import com.sentryc.graphqlapi.sales.dto.SellerPageableResponse;
import com.sentryc.graphqlapi.sales.dto.SellerSortBy;
import com.sentryc.graphqlapi.sales.service.SellerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

@Controller
@Slf4j
@Validated
public class SellerController {
    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @QueryMapping
    public SellerPageableResponse sellers(@Valid @Argument SellerFilter filter, @Valid @Argument PageInput page, @Argument SellerSortBy sortBy) {
        log.info("Fetching sellers by filter: {}", filter);
        SellerPageableResponse response = sellerService.getSellers(filter, page, sortBy);
        log.info("Retrieved seller pageable response: {}", response);
        return response;
    }
}
