package com.pintailconsultingllc.product.services

import com.ninjasquad.springmockk.MockkBean
import com.pintailconsultingllc.product.jpa.entities.Category
import com.pintailconsultingllc.product.jpa.repositories.CategoryRepository
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
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime
import java.util.*


@SpringBootTest
@DisplayName("CategoryService unit tests")
internal class CategoryServiceTest {

    @MockkBean
    lateinit var categoryRepository: CategoryRepository

    @Autowired
    lateinit var service: CategoryService

    private val expectedId = UUID.randomUUID()
    private val expectedName = "Foobar"

    @Nested
    @DisplayName("getCategoryPage")
    inner class GetCategoryPage {
        private val expectedPageable = Pageable.unpaged()
        private val expectedPage = PageImpl(emptyList<Category>())

        @Test
        fun `should return a page of category entities`() {
            every { categoryRepository.findAll(expectedPageable) } returns expectedPage

            val actualPage = service.getCategoryPage(expectedPageable)

            assertSame(actualPage, expectedPage)
            verify { categoryRepository.findAll(expectedPageable) }
        }
    }

    @Nested
    @DisplayName("createCategory")
    inner class CreateCategory {
        private val expectedCategory = createCategoryEntity(
            name = expectedName
        )
        private var actualCategory: Category? = null

        @BeforeEach
        fun doBeforeEachTest() {
            every { categoryRepository.save(any()) } returns expectedCategory

            actualCategory = service.createCategory(expectedName)
        }

        @Test
        fun `should invoke save on the category repository collaborator`() {
            verify { categoryRepository.save(any()) }
        }

        @Test
        fun `should return a persistent category entity`() {
            assertEquals(actualCategory?.name, expectedName)
        }
    }

    @Nested
    @DisplayName("updateCategory")
    inner class UpdateCategory {
        private val expectedCategory = createCategoryEntity(
            name = "Barfoo"
        )
        private var actualCategory: Category? = null

        @BeforeEach
        fun doBeforeEachTest() {
            every { categoryRepository.getOne(expectedId) } returns expectedCategory
            every { categoryRepository.save(any()) } returns expectedCategory

            actualCategory = service.updateCategory(expectedId, expectedName)
        }

        @Test
        fun `should invoke getOne on the category repository collaborator, retrieving the entity by ID`() {
            verify { categoryRepository.getOne(expectedId) }
        }

        @Test
        fun `should invoke save on the category repository collaborator`() {
            verify { categoryRepository.save(any()) }
        }

        @Test
        fun `should return a persistent category entity`() {
            assertEquals(actualCategory?.name, expectedName)
        }
    }

    @Nested
    @DisplayName("deleteCategory")
    inner class DeleteCategory {
        private val categorySlot = slot<Category>()
        private val expectedCategory = createCategoryEntity(
            name = expectedName
        )

        @BeforeEach
        fun doBeforeEachTest() {
            every { categoryRepository.getOne(expectedId) } returns expectedCategory
            every { categoryRepository.save(capture(categorySlot)) } returns expectedCategory

            service.deleteCategory(expectedId)
        }

        @Test
        fun `should invoke getOne on the category repository collaborator, retrieving the entity by ID`() {
            verify { categoryRepository.getOne(expectedId) }
        }

        @Test
        fun `should invoke save on the category repository collaborator`() {
            verify { categoryRepository.save(any()) }
        }

        @Test
        fun `should soft delete the persistent category entity`() {
            assertTrue(categorySlot.captured.deleted)
        }
    }

    private fun createCategoryEntity(name: String): Category {
        return Category(
            id = expectedId,
            name = name,
            version = 0,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            createdBy = "system",
            updatedBy = "system"
        )
    }
}
