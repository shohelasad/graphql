package com.sentryc.graphqlapi.pagination.dto;

import java.io.Serializable;

public record PageMeta(long totalElements, long totalPages, long pageNumber, long pageSize) implements Serializable {
}
