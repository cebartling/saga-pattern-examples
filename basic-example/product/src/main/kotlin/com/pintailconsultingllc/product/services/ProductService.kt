package com.pintailconsultingllc.product.services

import com.pintailconsultingllc.product.jpa.entities.Product
import com.pintailconsultingllc.product.jpa.repositories.ProductRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductService(val productRepository: ProductRepository) {

    fun getProductPage(pageable: Pageable): Page<Product> {
        return productRepository.findAll(pageable)
    }

    fun createProduct(name: String, sku: String): Product {
        val product = Product(UUID.randomUUID(), 0, name, sku)
        return productRepository.save(product)
    }

    fun updateProduct(id: UUID, name: String, sku: String): Product {
        val product = productRepository.getOne(id)
        product.name = name
        product.sku = sku
        return productRepository.save(product)
    }

    fun deleteProduct(id: UUID) {
        val product = productRepository.getOne(id)
        product.deleted = true
        productRepository.save(product)
    }
}