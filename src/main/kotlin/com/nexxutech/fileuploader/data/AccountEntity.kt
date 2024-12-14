package com.nexxutech.fileuploader.data

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "accounts")
data class AccountEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var accountId: String = "", // default value for accountId

    @Column(nullable = false)
    var name: String = "", // default value for name

    @Column(nullable = false)
    val isApproved: Boolean = true,

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime? = null
) {
    // No-argument constructor is automatically provided by Kotlin with default values
}
