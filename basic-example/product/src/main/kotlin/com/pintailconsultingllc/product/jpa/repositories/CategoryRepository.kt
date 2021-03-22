package com.pintailconsultingllc.product.jpa.repositories

import com.pintailconsultingllc.product.jpa.entities.Category
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CategoryRepository : JpaRepository<Category, UUID> {
    fun findByName(name: String): Category?
}