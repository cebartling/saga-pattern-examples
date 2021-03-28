package com.pintailconsultingllc.product.factories

import com.pintailconsultingllc.product.jpa.entities.Category
import com.pintailconsultingllc.product.jpa.entities.Product
import java.time.LocalDateTime
import java.util.*

fun createCategoryEntity(id: UUID, name: String): Category {
    return Category(
        id = id,
        name = name,
        version = 0,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        createdBy = "system",
        updatedBy = "system"
    )
}

fun createProductEntity(id: UUID, name: String, sku: String): Product {
    return Product(
        id = id,
        sku = sku,
        name = name,
        version = 0,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        createdBy = "system",
        updatedBy = "system"
    )
}
