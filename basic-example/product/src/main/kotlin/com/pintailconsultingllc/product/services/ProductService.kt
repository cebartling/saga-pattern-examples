package com.pintailconsultingllc.product.services

import com.pintailconsultingllc.product.jpa.entities.Category
import com.pintailconsultingllc.product.jpa.entities.Product
import com.pintailconsultingllc.product.jpa.repositories.ProductRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

/**
 * Service layer for retrieving and persisting product information.
 */
@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    /**
     * Retrieves a page of product entities.
     *
     * @return [Pageable] of [Product] entities.
     */
    fun getProductPage(pageable: Pageable) = productRepository.findAll(pageable)

    /**
     * Creates a new product entity.
     *
     * @param name [String] representing the product's name.
     * @param sku [String] representing the product's SKU.
     * @return A persistent [Product] entity instance.
     */
    fun createProduct(name: String, sku: String): Product {
        val product = Product(name = name, sku = sku)
        return productRepository.save(product)
    }

    /**
     * Updates an existing, persistent product entity.
     *
     * @param id [java.util.UUID] representing the product entity's primary identifier.
     * @param name [String] representing the product's name.
     * @param sku [String] representing the product's SKU.
     * @return A persistent [Product] entity instance.
     */
    fun updateProduct(id: UUID, name: String, sku: String): Product {
        val product = productRepository.getOne(id)
        product.name = name
        product.sku = sku
        return productRepository.save(product)
    }


    /**
     * Soft-deletes an existing, persistent product entity.
     *
     * @param id [java.util.UUID] representing the product entity's primary identifier.
     */
    fun deleteProduct(id: UUID) {
        val product = productRepository.getOne(id)
        product.deleted = true
        productRepository.save(product)
    }
}
