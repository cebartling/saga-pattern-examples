package com.pintailconsultingllc.product.controllers.v1

import com.ninjasquad.springmockk.MockkBean
import com.pintailconsultingllc.product.controllers.dto.ProductDTO
import com.pintailconsultingllc.product.controllers.inputs.ProductInput
import com.pintailconsultingllc.product.factories.createProductEntity
import com.pintailconsultingllc.product.jpa.entities.Product
import com.pintailconsultingllc.product.services.ProductService
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*

@SpringBootTest
@DisplayName("ProductController unit tests")
internal class ProductControllerTest {

    @MockkBean
    lateinit var productServiceMock: ProductService

    @Autowired
    lateinit var controller: ProductController

    @Nested
    @DisplayName("getProductsPage")
    inner class GetProductsPage {
        private val expectedPageable = Pageable.unpaged()
        private val expectedPage = PageImpl(
            listOf(
                createProductEntity(id = UUID.randomUUID(), name = "foobar1", sku = "3472379"),
                createProductEntity(id = UUID.randomUUID(), name = "foobar2", sku = "5345784"),
                createProductEntity(id = UUID.randomUUID(), name = "foobar3", sku = "6734574"),
                createProductEntity(id = UUID.randomUUID(), name = "foobar4", sku = "9348587")
            )
        )
        private var actualResponse: ResponseEntity<Page<ProductDTO>>? = null

        @Nested
        @DisplayName("successful API invocation")
        inner class Success {
            @BeforeEach
            fun doBeforeEachTest() {
                every { productServiceMock.getProductPage(expectedPageable) } returns expectedPage
                actualResponse = controller.getProductsPage(pageable = expectedPageable)
            }

            @Test
            fun `should return a 200 (OK) HTTP status code`() {
                assertThat(actualResponse?.statusCode).isEqualTo(HttpStatus.OK)
            }

            @Test
            fun `should invoke getProductPage on product service collaborator`() {
                verify { productServiceMock.getProductPage(expectedPageable) }
            }
        }
    }

    @Nested
    @DisplayName("createProduct")
    inner class CreateProduct {
        private var actualResponse: ResponseEntity<ProductDTO>? = null
        private val productInput: ProductInput = ProductInput(name = "foobar", sku = "9348587")
        private val product: Product = createProductEntity(
            id = UUID.randomUUID(),
            name = productInput.name,
            sku = productInput.sku
        )

        @Nested
        @DisplayName("successful API invocation")
        inner class Success {
            @BeforeEach
            fun doBeforeEachTest() {
                every {
                    productServiceMock.createProduct(
                        name = productInput.name,
                        sku = productInput.sku
                    )
                } returns product
                actualResponse = controller.createProduct(productInput = productInput)
            }

            @Test
            fun `should return a 201 (Created) HTTP status code`() {
                assertThat(actualResponse?.statusCode).isEqualTo(HttpStatus.CREATED)
            }

            @Test
            fun `should have the location header set to the new product resource URI`() {
                val expected = "/api/v1/products/${product.id}"
                val actual = actualResponse?.headers?.get("location")?.get(0)
                assertThat(actual).isEqualTo(expected)
            }

            @Test
            fun `should invoke createProduct on product service collaborator`() {
                verify { productServiceMock.createProduct(name = productInput.name, sku = productInput.sku) }
            }
        }
    }

    @Nested
    @DisplayName("updateProduct")
    inner class UpdateProduct {
        private var actualResponse: ResponseEntity<ProductDTO>? = null
        private val productInput: ProductInput = ProductInput(name = "barfoo", sku = "8797345")
        private val product: Product = createProductEntity(
            id = UUID.randomUUID(),
            name = "foobar",
            sku = "7348749"
        )

        @Nested
        @DisplayName("successful API invocation")
        inner class Success {
            @BeforeEach
            fun doBeforeEachTest() {
                every {
                    productServiceMock.updateProduct(
                        id = product.id,
                        name = productInput.name,
                        sku = productInput.sku
                    )
                } returns product
                actualResponse = controller.updateProduct(id = product.id, productInput = productInput)
            }

            @Test
            fun `should return a 204 (No Content) HTTP status code`() {
                assertThat(actualResponse?.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
            }

            @Test
            fun `should invoke updateProduct on product service collaborator`() {
                verify {
                    productServiceMock.updateProduct(
                        id = product.id,
                        name = productInput.name,
                        sku = productInput.sku
                    )
                }
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct")
    inner class DeleteProduct {
        private var actualResponse: ResponseEntity<ProductDTO>? = null
        private val product: Product = createProductEntity(
            id = UUID.randomUUID(),
            name = "foobar",
            sku = "3478954"
        )

        @Nested
        @DisplayName("successful API invocation")
        inner class Success {
            @BeforeEach
            fun doBeforeEachTest() {
                every { productServiceMock.deleteProduct(id = product.id) } just Runs
                actualResponse = controller.deleteProduct(id = product.id)
            }

            @Test
            fun `should return a 204 (No Content) HTTP status code`() {
                assertThat(actualResponse?.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
            }

            @Test
            fun `should invoke deleteProduct on product service collaborator`() {
                verify { productServiceMock.deleteProduct(id = product.id) }
            }
        }
    }
}
