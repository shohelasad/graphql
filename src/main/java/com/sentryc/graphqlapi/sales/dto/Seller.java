package com.sentryc.graphqlapi.sales.dto;

import com.sentryc.graphqlapi.products.dto.ProducerSellerState;

import java.io.Serializable;
import java.util.Set;

public record Seller(String sellerName, String externalId, Set<ProducerSellerState> producerSellerStates, String marketplaceId) implements Serializable {}