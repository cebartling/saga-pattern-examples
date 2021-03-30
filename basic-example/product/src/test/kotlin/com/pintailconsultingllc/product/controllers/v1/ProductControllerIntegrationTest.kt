package com.pintailconsultingllc.product.controllers.v1

import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

@DisplayName("ProductController integration tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerIntegrationTest(
    @Autowired val restTemplate: TestRestTemplate
) {
//    @Nested
//    @DisplayName("GET /api/v1/products")
//    inner class GetProductsPage {
//        private var entity: ResponseEntity<Page<Product>>? = null
//
//        @BeforeEach
//        fun doBeforeEachTest() {
//            entity = restTemplate.getForEntity<Product>("/api/v1/products", <Page<Product>>,"page", 0, "size", 100)
//        }
//
//        @Test
//        fun `should respond with a 200 (OK) status code`() {
//
//            assertThat(entity?.statusCode).isEqualTo(HttpStatus.OK)
//        }
//    }
}