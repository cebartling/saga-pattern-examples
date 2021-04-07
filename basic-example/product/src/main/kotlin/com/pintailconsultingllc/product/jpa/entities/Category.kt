package com.pintailconsultingllc.product.jpa.entities

import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.Table
import javax.persistence.Version
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "categories")
data class Category(
    @Id @GeneratedValue
    val id: UUID = UUID.randomUUID(),

    @Version val version: Int = 0,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "deleted", nullable = false)
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

    @ManyToMany
    var products: Set<Product> = emptySet(),
)
