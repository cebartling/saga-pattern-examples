package com.pintailconsultingllc.product.services

import com.pintailconsultingllc.product.jpa.entities.Category
import com.pintailconsultingllc.product.jpa.repositories.CategoryRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class CategoryService(val categoryRepository: CategoryRepository) {

    fun getCategoryPage(pageable: Pageable) = categoryRepository.findAll(pageable)

    fun createCategory(name: String): Category {
        val category = Category(name = name)
        return categoryRepository.save(category)
    }

    fun updateCategory(id: UUID, name: String): Category {
        val category = categoryRepository.getOne(id)
        category.name = name
        return categoryRepository.save(category)
    }

    fun deleteCategory(id: UUID) {
        val category = categoryRepository.getOne(id)
        category.deleted = true
        categoryRepository.save(category)
    }
}