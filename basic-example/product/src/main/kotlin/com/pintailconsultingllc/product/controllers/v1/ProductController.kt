package com.pintailconsultingllc.product.controllers.v1

import com.pintailconsultingllc.product.controllers.inputs.ProductInput
import com.pintailconsultingllc.product.jpa.entities.Product
import com.pintailconsultingllc.product.services.ProductService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/api/v1/products")
class ProductController(val productService: ProductService) {

    @GetMapping
    fun getProductsPage(
        pageable: Pageable
    ): Page<Product> {
        return productService.getProductPage(pageable)
    }

    @PostMapping
    fun createProduct(
        @RequestBody productInput: ProductInput
    ): ResponseEntity<Product> {
        val product = productService.createProduct(productInput.name, productInput.sku)
        val location = URI("/api/v1/products/${product.id}")
        return ResponseEntity.created(location).build()
    }

    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: UUID,
        @RequestBody productInput: ProductInput
    ): ResponseEntity<Product> {
        productService.updateProduct(id, productInput.name, productInput.sku)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(
        @PathVariable id: UUID
    ): ResponseEntity<Product> {
        productService.deleteProduct(id)
        return ResponseEntity.noContent().build()
    }
}