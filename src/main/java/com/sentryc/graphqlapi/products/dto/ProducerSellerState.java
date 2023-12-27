package com.sentryc.graphqlapi.products.dto;

import com.sentryc.graphqlapi.sales.dto.SellerState;

import java.io.Serializable;
import java.util.UUID;

public record ProducerSellerState(UUID producerId, String producerName, SellerState sellerState, UUID sellerId) implements Serializable {
}
