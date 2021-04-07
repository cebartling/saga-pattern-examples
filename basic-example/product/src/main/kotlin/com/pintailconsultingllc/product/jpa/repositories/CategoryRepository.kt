package com.pintailconsultingllc.product.jpa.repositories

import com.pintailconsultingllc.product.jpa.entities.Category
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, UUID> {
    fun findByName(name: String): Category?
}
