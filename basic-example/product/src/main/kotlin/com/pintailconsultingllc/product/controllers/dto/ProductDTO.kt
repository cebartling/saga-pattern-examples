package com.pintailconsultingllc.product.controllers.dto

import com.pintailconsultingllc.product.jpa.entities.Product
import java.time.LocalDateTime
import java.util.*

data class ProductDTO(
    val id: UUID,
    val version: Int,
    val name: String,
    val sku: String,
    val deleted: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val createdBy: String,
    val updatedBy: String,
) {
    constructor(entity: Product): this(
        id = entity.id,
        version = entity.version,
        name = entity.name,
        sku = entity.sku,
        deleted = entity.deleted,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
        createdBy = entity.createdBy,
        updatedBy = entity.updatedBy
    )
}
