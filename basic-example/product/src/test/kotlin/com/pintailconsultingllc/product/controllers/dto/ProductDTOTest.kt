package com.pintailconsultingllc.product.controllers.dto

import com.pintailconsultingllc.product.factories.createProductEntity
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("ProductDTO unit tests")
internal class ProductDTOTest {

    private val entity = createProductEntity(
        id = UUID.randomUUID(),
        sku = "34897598",
        name = "Foobar"
    )
    private var productDTO: ProductDTO? = null

    @Nested
    @DisplayName("constructor")
    inner class ContructorTests {

        @BeforeEach
        fun doBeforeEachTest() {
            productDTO = ProductDTO(entity = entity)
        }

        @Test
        fun `should set the data transfer object's ID`() {
            Assertions.assertThat(productDTO?.id).isEqualTo(entity.id)
        }

        @Test
        fun `should set the data transfer object's name`() {
            Assertions.assertThat(productDTO?.name).isEqualTo(entity.name)
        }

        @Test
        fun `should set the data transfer object's SKU`() {
            Assertions.assertThat(productDTO?.sku).isEqualTo(entity.sku)
        }

        @Test
        fun `should set the data transfer object's version`() {
            Assertions.assertThat(productDTO?.version).isEqualTo(entity.version)
        }

        @Test
        fun `should set the data transfer object's created at timestamp`() {
            Assertions.assertThat(productDTO?.createdAt).isEqualTo(entity.createdAt)
        }

        @Test
        fun `should set the data transfer object's updated at timestamp`() {
            Assertions.assertThat(productDTO?.updatedAt).isEqualTo(entity.updatedAt)
        }

        @Test
        fun `should set the data transfer object's created by string`() {
            Assertions.assertThat(productDTO?.createdBy).isEqualTo(entity.createdBy)
        }

        @Test
        fun `should set the data transfer object's updated by string`() {
            Assertions.assertThat(productDTO?.updatedBy).isEqualTo(entity.updatedBy)
        }
    }
}
