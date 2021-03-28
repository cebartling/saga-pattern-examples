package com.pintailconsultingllc.product.controllers.dto

import com.pintailconsultingllc.product.factories.createCategoryEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("CategoryDTO unit tests")
internal class CategoryDTOTest {

    private val entity = createCategoryEntity(
        id = UUID.randomUUID(),
        name = "Foobar"
    )
    private var categoryDTO: CategoryDTO? = null

    @Nested
    @DisplayName("constructor")
    inner class ContructorTests {

        @BeforeEach
        fun doBeforeEachTest() {
            categoryDTO = CategoryDTO(entity = entity)
        }

        @Test
        fun `should set the data transfer object's ID`() {
            assertThat(categoryDTO?.id).isEqualTo(entity.id)
        }

        @Test
        fun `should set the data transfer object's name`() {
            assertThat(categoryDTO?.name).isEqualTo(entity.name)
        }

        @Test
        fun `should set the data transfer object's version`() {
            assertThat(categoryDTO?.version).isEqualTo(entity.version)
        }

        @Test
        fun `should set the data transfer object's created at timestamp`() {
            assertThat(categoryDTO?.createdAt).isEqualTo(entity.createdAt)
        }

        @Test
        fun `should set the data transfer object's updated at timestamp`() {
            assertThat(categoryDTO?.updatedAt).isEqualTo(entity.updatedAt)
        }

        @Test
        fun `should set the data transfer object's created by string`() {
            assertThat(categoryDTO?.createdBy).isEqualTo(entity.createdBy)
        }

        @Test
        fun `should set the data transfer object's updated by string`() {
            assertThat(categoryDTO?.updatedBy).isEqualTo(entity.updatedBy)
        }
    }
}
