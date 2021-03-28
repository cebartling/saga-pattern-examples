package com.pintailconsultingllc.product.services

import com.ninjasquad.springmockk.MockkBean
import com.pintailconsultingllc.product.jpa.entities.Category
import com.pintailconsultingllc.product.jpa.repositories.CategoryRepository
import io.mockk.every
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
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
@DisplayName("CategoryService tests")
internal class CategoryServiceTest {

    @MockkBean
    lateinit var categoryRepository: CategoryRepository

    @Autowired
    lateinit var service: CategoryService

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
        private val expectedName = "Foobar"
        private val expectedCategory = Category(
            id = UUID.randomUUID(),
            name = expectedName,
            version = 0,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            createdBy = "system",
            updatedBy = "system"
        )

        @Test
        fun `should return a persistent category entity`() {
            every { categoryRepository.save(any()) } returns expectedCategory

            val actualCategory = service.createCategory(expectedName)

            assertEquals(actualCategory.name, expectedName)
            verify { categoryRepository.save(any()) }
        }
    }

    @Nested
    @DisplayName("updateCategory")
    inner class UpdateCategory {
        private val expectedName = "Foobar"
        private val expectedId = UUID.randomUUID()
        private val expectedCategory = Category(
            id = expectedId,
            name = "Barfoo",
            version = 0,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            createdBy = "system",
            updatedBy = "system"
        )

        @Test
        fun `should update the name of the category in the database`() {
            every { categoryRepository.getOne(expectedId) } returns expectedCategory
            every { categoryRepository.save(any()) } returns expectedCategory

            val actualCategory = service.updateCategory(expectedId, expectedName)

            assertEquals(actualCategory.name, expectedName)
            verify { categoryRepository.getOne(expectedId) }
            verify { categoryRepository.save(any()) }
        }
    }

    @Nested
    @DisplayName("deleteCategory")
    inner class DeleteCategory {
        private val expectedId = UUID.randomUUID()
        private val categorySlot = slot<Category>()
        private val expectedCategory = Category(
            id = expectedId,
            name = "Barfoo",
            version = 0,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            createdBy = "system",
            updatedBy = "system"
        )

        @Test
        fun `should soft delete the persistent category entity`() {
            every { categoryRepository.getOne(expectedId) } returns expectedCategory
            every { categoryRepository.save(capture(categorySlot)) } returns expectedCategory

            service.deleteCategory(expectedId)

            assertTrue(categorySlot.captured.deleted)
            verify { categoryRepository.getOne(expectedId) }
            verify { categoryRepository.save(any()) }
        }
    }
}
