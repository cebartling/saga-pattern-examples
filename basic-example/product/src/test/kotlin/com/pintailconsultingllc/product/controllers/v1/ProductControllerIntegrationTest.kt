package com.pintailconsultingllc.product.controllers.v1

import com.pintailconsultingllc.product.jpa.entities.Product
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.data.domain.PageImpl
import org.springframework.http.ResponseEntity


@DisplayName("ProductController integration tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
class ProductControllerIntegrationTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    val pageProductResponseType = object : ParameterizedTypeReference<PageImpl<Product?>?>() {}

    @Nested
    @DisplayName("GET /api/v1/products")
    inner class GetProductsPage {
        private var entity: ResponseEntity<PageImpl<Product>>? = null

//        @BeforeEach
//        fun doBeforeEachTest() {
//            val uriVariables = mapOf("size" to 100, "page" to 0)
//            entity = restTemplate.getForEntity("/api/v1/products", pageProductResponseType, uriVariables)
//        }
//
//        @Test
//        fun `should respond with a 200 (OK) status code`() {
//            assertThat(entity?.statusCode).isEqualTo(HttpStatus.OK)
//        }
    }
}