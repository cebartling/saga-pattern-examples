package com.pintailconsultingllc.product.controllers.v1

import com.pintailconsultingllc.product.controllers.dto.CategoryDTO
import com.pintailconsultingllc.product.controllers.inputs.CategoryInput
import com.pintailconsultingllc.product.jpa.entities.Category
import com.pintailconsultingllc.product.services.CategoryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/api/v1/categories")
class CategoryController(val categoryService: CategoryService) {

    @Operation(summary = "Get product categories, optionally filtered.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Found product categories", content = [
                    (Content(
                        mediaType = "application/json", array = (
                                ArraySchema(schema = Schema(implementation = Category::class)))
                    ))]
            ),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
            ApiResponse(responseCode = "404", description = "Did not find any product categories", content = [Content()])]
    )
    @GetMapping
    fun getCategoriesPage(
        pageable: Pageable
    ) = ResponseEntity.ok(categoryService.getCategoryPage(pageable).map { CategoryDTO(it) })

    @Operation(summary = "Create a new category.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201", description = "New category created!", content = [
                    (Content(
                        mediaType = "application/json",
                        array = (ArraySchema(schema = Schema(implementation = Category::class)))
                    ))]
            ),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()])
        ]
    )
    @PostMapping
    fun createCategory(
        @RequestBody categoryInput: CategoryInput
    ): ResponseEntity<CategoryDTO> {
        val category = categoryService.createCategory(categoryInput.name)
        val location = URI("/api/v1/categories/${category.id}")
        return ResponseEntity.created(location).build()
    }

    @Operation(summary = "Update the category.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Category updated"),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
            ApiResponse(responseCode = "404", description = "Did not find the category", content = [Content()])
        ]
    )
    @PutMapping("/{id}")
    fun updateCategory(
        @PathVariable id: UUID,
        @RequestBody categoryInput: CategoryInput
    ): ResponseEntity<CategoryDTO> {
        categoryService.updateCategory(id, categoryInput.name)
        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "Soft delete a category.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Category soft deleted."),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
            ApiResponse(responseCode = "404", description = "Did not find the category", content = [Content()])]
    )
    @DeleteMapping("/{id}")
    fun deleteCategory(
        @PathVariable id: UUID
    ): ResponseEntity<CategoryDTO> {
        categoryService.deleteCategory(id)
        return ResponseEntity.noContent().build()
    }
}