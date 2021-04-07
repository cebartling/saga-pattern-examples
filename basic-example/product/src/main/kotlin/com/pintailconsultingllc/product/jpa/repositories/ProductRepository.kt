package com.pintailconsultingllc.product.jpa.repositories

import com.pintailconsultingllc.product.jpa.entities.Product
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, UUID> {
    fun findByName(name: String): Product?
    fun findBySku(sku: String): Product?
}
