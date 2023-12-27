package com.sentryc.graphqlapi.sales.utils;

import com.sentryc.graphqlapi.sales.dto.SellerSortBy;
import org.springframework.data.domain.Sort;

public class SortingUtil {
    public static Sort getSort(SellerSortBy sortBy) {
        switch (sortBy) {
            case NAME_ASC:
                return Sort.by("sellerName").ascending();
            case NAME_DESC:
                return Sort.by("sellerName").descending();
            case MARKETPLACE_ID_ASC:
                return Sort.by("marketplace.id").ascending();
            case MARKETPLACE_ID_DESC:
                return Sort.by("marketplace.id").descending();
            case SELLER_INFO_EXTERNAL_ID_ASC:
                return Sort.by("externalId").ascending();
            case SELLER_INFO_EXTERNAL_ID_DESC:
                return Sort.by("externalId").descending();
            default:
                return Sort.unsorted();
        }
    }
}
