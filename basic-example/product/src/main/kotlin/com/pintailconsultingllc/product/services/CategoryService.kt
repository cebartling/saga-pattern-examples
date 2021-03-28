package com.pintailconsultingllc.product.services

import com.pintailconsultingllc.product.jpa.entities.Category
import com.pintailconsultingllc.product.jpa.repositories.CategoryRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

/**
 * Service layer for retrieving and persisting product category information.
 */
@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {

    /**
     * Retrieves a page of category entities.
     *
     * @return [Pageable] of [Category] entities.
     */
    fun getCategoryPage(pageable: Pageable) = categoryRepository.findAll(pageable)

    /**
     * Creates a new category entity.
     *
     * @param name [String] representing the category's name.
     * @return [Category] entity instance, which is persistent.
     */
    fun createCategory(name: String): Category {
        val category = Category(name = name)
        return categoryRepository.save(category)
    }

    /**
     * Updates an existing, persistent category entity.
     *
     * @param id [java.util.UUID] instance that represents the category entity's primary identifier.
     * @param name [String] representing the category's name.
     * @return [Category] entity instance, which is persistent.
     */
    fun updateCategory(id: UUID, name: String): Category {
        val category = categoryRepository.getOne(id)
        category.name = name
        return categoryRepository.save(category)
    }

    /**
     * Soft-deletes an existing, persistent category entity.
     *
     * @param id [java.util.UUID] instance that represents the category entity's primary identifier.
     */
    fun deleteCategory(id: UUID) {
        val category = categoryRepository.getOne(id)
        category.deleted = true
        categoryRepository.save(category)
    }
}
