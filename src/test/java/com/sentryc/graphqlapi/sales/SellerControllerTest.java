package com.sentryc.graphqlapi.sales;

import com.sentryc.graphqlapi.sales.dto.SellerPageableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureGraphQlTester
class SellerControllerTest {

    @Autowired
    private GraphQlTester tester;

    @Test
    void findWithFilter() {
        String query = "{sellers(filter: { searchByName: \"Amazon\"} page: { page: 0, size: 10 }, sortBy: NAME_ASC) { meta { pageNumber pageSize totalPages totalElements } data { sellerName externalId producerSellerStates { producerId producerName sellerState sellerId } marketplaceId } } }";
        SellerPageableResponse response = tester.document(query)
                .execute()
                .path("data.sellers")
                .entity(SellerPageableResponse.class)
                .get();

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.meta());
        Assertions.assertNotNull(response.data());
        Assertions.assertTrue(response.data().size() > 0);
        Assertions.assertEquals(response.data().get(0).sellerName(), "Amazon Us");
    }
}