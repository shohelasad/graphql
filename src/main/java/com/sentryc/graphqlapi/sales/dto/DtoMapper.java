package com.sentryc.graphqlapi.sales.dto;

import com.sentryc.graphqlapi.products.dto.ProducerSellerState;
import com.sentryc.graphqlapi.sales.domains.SellerInfo;
import com.sentryc.graphqlapi.sales.domains.SellerProducer;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DtoMapper {
    public static Seller mapToSeller(String sellerName, String externalId, Set<ProducerSellerState> producerSellerStates, String marketplaceId) {
        return new Seller(sellerName, externalId, producerSellerStates, marketplaceId);
    }

    public static ProducerSellerState mapToProducerSellerState(SellerProducer sellerProducer) {
        return new ProducerSellerState(sellerProducer.getProducer().getId(),
                sellerProducer.getProducer().getName(), sellerProducer.getState(), sellerProducer.getId());
    }

    public static List<Seller> mapToSellers(List<SellerInfo> sellerInfos) {
        return sellerInfos.stream()
                .map(info -> mapToSeller(
                        info.getSellerName(),
                        info.getExternalId(),
                        info.getSellerProducers()
                            .stream().map(seller -> mapToProducerSellerState(seller))
                                .collect(Collectors.toSet()),
                        info.getMarketplace().getId()))
                .collect(Collectors.toList());
    }
}

