package com.pintailconsultingllc.product.controllers.dto

import com.pintailconsultingllc.product.jpa.entities.Category
import java.time.LocalDateTime
import java.util.*

data class CategoryDTO(
    val id: UUID,
    val version: Int,
    var name: String,
    val deleted: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val createdBy: String,
    val updatedBy: String,
) {
    constructor(entity: Category): this(
        id = entity.id,
        version = entity.version,
        name = entity.name,
        deleted = entity.deleted,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
        createdBy = entity.createdBy,
        updatedBy = entity.updatedBy
    )
}
