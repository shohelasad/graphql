package com.sentryc.graphqlapi.sales.service;

import com.sentryc.graphqlapi.pagination.dto.PageInput;
import com.sentryc.graphqlapi.sales.dto.SellerFilter;
import com.sentryc.graphqlapi.sales.dto.SellerPageableResponse;
import com.sentryc.graphqlapi.sales.dto.SellerSortBy;

public interface SellerService {
    SellerPageableResponse getSellers(SellerFilter filter, PageInput pageInput, SellerSortBy sortBy);
}
