package com.pintailconsultingllc.product.jpa.repositories

import com.pintailconsultingllc.product.jpa.entities.Product
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProductRepository : JpaRepository<Product, UUID>

