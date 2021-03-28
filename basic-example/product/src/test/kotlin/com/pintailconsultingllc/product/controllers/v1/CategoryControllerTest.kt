package com.pintailconsultingllc.product.controllers.v1

import com.ninjasquad.springmockk.MockkBean
import com.pintailconsultingllc.product.controllers.dto.CategoryDTO
import com.pintailconsultingllc.product.controllers.inputs.CategoryInput
import com.pintailconsultingllc.product.factories.createCategoryEntity
import com.pintailconsultingllc.product.jpa.entities.Category
import com.pintailconsultingllc.product.services.CategoryService
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
@DisplayName("CategoryController unit tests")
internal class CategoryControllerTest {

    @MockkBean
    lateinit var categoryServiceMock: CategoryService

    @Autowired
    lateinit var controller: CategoryController

    @Nested
    @DisplayName("getCategoriesPage")
    inner class GetCategoriesPage {
        private val expectedPageable = Pageable.unpaged()
        private val expectedPage = PageImpl(
            listOf(
                createCategoryEntity(id = UUID.randomUUID(), name = "foobar1"),
                createCategoryEntity(id = UUID.randomUUID(), name = "foobar2"),
                createCategoryEntity(id = UUID.randomUUID(), name = "foobar3"),
                createCategoryEntity(id = UUID.randomUUID(), name = "foobar4")
            )
        )
        private var actualResponse: ResponseEntity<Page<CategoryDTO>>? = null

        @Nested
        @DisplayName("successful API invocation")
        inner class Success {
            @BeforeEach
            fun doBeforeEachTest() {
                every { categoryServiceMock.getCategoryPage(expectedPageable) } returns expectedPage
                actualResponse = controller.getCategoriesPage(pageable = expectedPageable)
            }

            @Test
            fun `should return a 200 (OK) HTTP status code`() {
                assertThat(actualResponse?.statusCode).isEqualTo(HttpStatus.OK)
            }

            @Test
            fun `should invoke getCategoryPage on category service collaborator`() {
                verify { categoryServiceMock.getCategoryPage(expectedPageable) }
            }
        }
    }

    @Nested
    @DisplayName("createCategory")
    inner class CreateCategory {
        private var actualResponse: ResponseEntity<CategoryDTO>? = null
        private val categoryInput: CategoryInput = CategoryInput(name = "foobar")
        private val category: Category = createCategoryEntity(
            id = UUID.randomUUID(),
            name = categoryInput.name
        )

        @Nested
        @DisplayName("successful API invocation")
        inner class Success {
            @BeforeEach
            fun doBeforeEachTest() {
                every { categoryServiceMock.createCategory(name = categoryInput.name) } returns category
                actualResponse = controller.createCategory(categoryInput = categoryInput)
            }

            @Test
            fun `should return a 201 (Created) HTTP status code`() {
                assertThat(actualResponse?.statusCode).isEqualTo(HttpStatus.CREATED)
            }

            @Test
            fun `should have the location header set to the new category resource URI`() {
                val expected = "/api/v1/categories/${category.id}"
                val actual = actualResponse?.headers?.get("location")?.get(0)
                assertThat(actual).isEqualTo(expected)
            }

            @Test
            fun `should invoke createCategory on category service collaborator`() {
                verify { categoryServiceMock.createCategory(name = categoryInput.name) }
            }
        }
    }

    @Nested
    @DisplayName("updateCategory")
    inner class UpdateCategory {
        private var actualResponse: ResponseEntity<CategoryDTO>? = null
        private val categoryInput: CategoryInput = CategoryInput(name = "barfoo")
        private val category: Category = createCategoryEntity(
            id = UUID.randomUUID(),
            name = "foobar"
        )

        @Nested
        @DisplayName("successful API invocation")
        inner class Success {
            @BeforeEach
            fun doBeforeEachTest() {
                every { categoryServiceMock.updateCategory(id = category.id, name = categoryInput.name) } returns category
                actualResponse = controller.updateCategory(id = category.id, categoryInput = categoryInput)
            }

            @Test
            fun `should return a 204 (No Content) HTTP status code`() {
                assertThat(actualResponse?.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
            }

            @Test
            fun `should invoke updateCategory on category service collaborator`() {
                verify { categoryServiceMock.updateCategory(id = category.id, name = categoryInput.name) }
            }
        }
    }

    @Nested
    @DisplayName("deleteCategory")
    inner class DeleteCategory {
        private var actualResponse: ResponseEntity<CategoryDTO>? = null
        private val category: Category = createCategoryEntity(
            id = UUID.randomUUID(),
            name = "foobar"
        )

        @Nested
        @DisplayName("successful API invocation")
        inner class Success {
            @BeforeEach
            fun doBeforeEachTest() {
                every { categoryServiceMock.deleteCategory(id = category.id) } just Runs
                actualResponse = controller.deleteCategory(id = category.id)
            }

            @Test
            fun `should return a 204 (No Content) HTTP status code`() {
                assertThat(actualResponse?.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
            }

            @Test
            fun `should invoke deleteCategory on category service collaborator`() {
                verify { categoryServiceMock.deleteCategory(id = category.id) }
            }
        }
    }
}
