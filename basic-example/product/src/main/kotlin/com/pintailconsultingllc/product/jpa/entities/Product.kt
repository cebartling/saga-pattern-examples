package com.pintailconsultingllc.product.jpa.entities

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "products")
class Product(
    @Id @GeneratedValue
    val id: UUID = UUID.randomUUID(),

    @Version val version: Int = 0,

    var name: String,
    var sku: String,
    var deleted: Boolean = false,

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "created_by")
    @CreatedBy
    var createdBy: String = "system",

    @Column(name = "updated_by")
    @LastModifiedBy
    var updatedBy: String = "system",
)