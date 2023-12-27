package com.sentryc.graphqlapi.sales.domains;

import com.sentryc.graphqlapi.products.domains.Producer;
import com.sentryc.graphqlapi.sales.dto.SellerState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="sellers", uniqueConstraints = @UniqueConstraint(columnNames = {"producer_id", "seller_info_id", "state"}))
public class SellerProducer {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "producer_id", nullable = false)
    private Producer producer;

    @ManyToOne
    @JoinColumn(name = "seller_info_id", nullable = false)
    private SellerInfo sellerInfo;

    @Enumerated(EnumType.STRING)
    private SellerState state;

    @Override
    public String toString() {
        return "SellerProducer{" +
                "id=" + id +
                ", producer=" + producer +
                ", sellerInfo=" + sellerInfo +
                ", state=" + state +
                '}';
    }
}
