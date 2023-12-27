package com.sentryc.graphqlapi.sales.dto;

import com.sentryc.graphqlapi.pagination.dto.PageMeta;

import java.io.Serializable;
import java.util.List;

public record SellerPageableResponse(PageMeta meta, List<Seller> data) implements Serializable {
}
