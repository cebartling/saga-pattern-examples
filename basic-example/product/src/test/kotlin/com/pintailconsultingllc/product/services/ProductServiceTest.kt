package com.pintailconsultingllc.product.services

import com.ninjasquad.springmockk.MockkBean
import com.pintailconsultingllc.product.factories.createProductEntity
import com.pintailconsultingllc.product.jpa.entities.Product
import com.pintailconsultingllc.product.jpa.repositories.ProductRepository
import io.mockk.every
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime
import java.util.*

@SpringBootTest
@DisplayName("ProductService unit tests")
internal class ProductServiceTest {

    @MockkBean
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var service: ProductService

    private val expectedId = UUID.randomUUID()
    private val expectedName = "Foobar"
    private val expectedSku = "23429378"

    @Nested
    @DisplayName("getProductPage")
    inner class GetProductPage {
        private val expectedPageable = Pageable.unpaged()
        private val expectedPage = PageImpl(emptyList<Product>())
        private var actualPage: Page<Product>? = null

        @BeforeEach
        fun doBeforeEachTest() {
            every { productRepository.findAll(expectedPageable) } returns expectedPage

            actualPage = service.getProductPage(expectedPageable)
        }

        @Test
        fun `should invoke finallAll on product repository collaborator, passing the pageable`() {
            verify { productRepository.findAll(expectedPageable) }
        }

        @Test
        fun `should return a page of Product entities`() {
            assertSame(actualPage, expectedPage)
        }
    }

    @Nested
    @DisplayName("createProduct")
    inner class CreateProduct {
        private val expectedProduct = createProductEntity(
            id = expectedId,
            name = expectedName,
            sku = expectedSku
        )
        private var actualProduct: Product? = null

        @BeforeEach
        fun doBeforeEachTest() {
            every { productRepository.save(any()) } returns expectedProduct

            actualProduct = service.createProduct(
                name = expectedName,
                sku = expectedSku
            )
        }

        @Test
        fun `should invoke save on the product repository collaborator`() {
            verify { productRepository.save(any()) }
        }

        @Test
        fun `should return a persistent Product entity`() {
            assertEquals(actualProduct?.name, expectedName)
        }
    }

    @Nested
    @DisplayName("updateProduct")
    inner class UpdateProduct {
        private val expectedProduct = createProductEntity(
            id = expectedId,
            name = "Barfoo",
            sku = expectedSku,
        )
        private var actualProduct: Product? = null

        @BeforeEach
        fun doBeforeEachTest() {
            every { productRepository.getOne(expectedId) } returns expectedProduct
            every { productRepository.save(any()) } returns expectedProduct

            actualProduct = service.updateProduct(
                id = expectedId,
                name = expectedName,
                sku = expectedSku
            )
        }

        @Test
        fun `should retrieve the persistent Product entity by ID`() {
            verify { productRepository.getOne(expectedId) }
        }

        @Test
        fun `should update the name of the Product in the database`() {
            assertEquals(actualProduct?.name, expectedName)
        }

        @Test
        fun `should save the modified persistent Product entity to the database`() {
            verify { productRepository.save(any()) }
        }
    }

    @Nested
    @DisplayName("deleteProduct")
    inner class DeleteProduct {
        private val productSlot = slot<Product>()
        private val expectedProduct = createProductEntity(
            id = expectedId,
            name = expectedName,
            sku = expectedSku
        )

        @BeforeEach
        fun doBeforeEachTest() {
            every { productRepository.getOne(expectedId) } returns expectedProduct
            every { productRepository.save(capture(productSlot)) } returns expectedProduct

            service.deleteProduct(expectedId)
        }

        @Test
        fun `should retrieve the persistent Product entity by ID`() {
            verify { productRepository.getOne(expectedId) }
        }

        @Test
        fun `should soft delete the persistent Product entity`() {
            assertTrue(productSlot.captured.deleted)
        }

        @Test
        fun `should save the modified persistent Product entity to the database`() {
            verify { productRepository.save(any()) }
        }
    }
}
