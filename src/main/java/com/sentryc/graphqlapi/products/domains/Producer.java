package com.sentryc.graphqlapi.products.domains;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="producers")
public class Producer {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private LocalDateTime createdAt;
}
