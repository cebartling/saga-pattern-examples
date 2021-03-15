package com.pintailconsultingllc.product.controllers.v1

import com.pintailconsultingllc.product.controllers.inputs.ProductInput
import com.pintailconsultingllc.product.jpa.entities.Product
import com.pintailconsultingllc.product.services.ProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/api/v1/products")
class ProductController(val productService: ProductService) {

    @Operation(summary = "Get products, optionally filtered.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Found products", content = [
                    (Content(
                        mediaType = "application/json", array = (
                                ArraySchema(schema = Schema(implementation = Product::class)))
                    ))]
            ),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
            ApiResponse(responseCode = "404", description = "Did not find any products", content = [Content()])]
    )
    @GetMapping
    fun getProductsPage(
        pageable: Pageable
    ): Page<Product> {
        return productService.getProductPage(pageable)
    }

    @Operation(summary = "Create a new product.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201", description = "New product created!", content = [
                    (Content(
                        mediaType = "application/json",
                        array = (ArraySchema(schema = Schema(implementation = Product::class)))
                    ))]
            ),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()])
        ]
    )
    @PostMapping
    fun createProduct(
        @RequestBody productInput: ProductInput
    ): ResponseEntity<Product> {
        val product = productService.createProduct(productInput.name, productInput.sku)
        val location = URI("/api/v1/products/${product.id}")
        return ResponseEntity.created(location).build()
    }

    @Operation(summary = "Update the product.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Product updated"),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
            ApiResponse(responseCode = "404", description = "Did not find the product", content = [Content()])
        ]
    )
    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: UUID,
        @RequestBody productInput: ProductInput
    ): ResponseEntity<Product> {
        productService.updateProduct(id, productInput.name, productInput.sku)
        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "Soft delete a product.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Product soft deleted."),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
            ApiResponse(responseCode = "404", description = "Did not find the product", content = [Content()])]
    )
    @DeleteMapping("/{id}")
    fun deleteProduct(
        @PathVariable id: UUID
    ): ResponseEntity<Product> {
        productService.deleteProduct(id)
        return ResponseEntity.noContent().build()
    }
}