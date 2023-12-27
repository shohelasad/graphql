package com.sentryc.graphqlapi.sales.domains;

import com.sentryc.graphqlapi.markets.domains.Marketplace;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="seller_infos")
public class SellerInfo {
    @Id
    @GeneratedValue
    private UUID id;

    private String sellerName;

    private String externalId;

    private String country;

    private String url;

    @ManyToOne
    @JoinColumn(name = "marketplace_id")
    private Marketplace marketplace;

    @OneToMany(mappedBy = "sellerInfo", cascade = CascadeType.ALL)
    private List<SellerProducer> sellerProducers = new ArrayList<>();

    @Override
    public String toString() {
        return "SellerInfo{" +
                "id=" + id +
                ", sellerName='" + sellerName + '\'' +
                ", url='" + url + '\'' +
                ", country='" + country + '\'' +
                ", externalId='" + externalId + '\'' +
                ", marketplace=" + marketplace +
                ", sellerProducers=" + sellerProducers +
                '}';
    }
}
