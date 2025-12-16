package com.example.testsecurity.model

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Id
import jakarta.persistence.GenerationType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Column
import jakarta.persistence.CollectionTable
import jakarta.persistence.ElementCollection
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import java.time.LocalDateTime
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.GrantedAuthority


@Entity
@Table(name = "users")  // PostgreSQL table name
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment ID
    val id: Long = 0,
    
    @Column(unique = true, nullable = false)  // Email must be unique and not null
    val email: String,
    
    @Column(nullable = false)  // Password cannot be null
    var passwordHash: String,
    
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    
    @ElementCollection(fetch = FetchType.EAGER)  // Load roles eagerly with user
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "role")
    val roles: MutableSet<String> = mutableSetOf("USER")  // Default role
) : UserDetails {
    
    // Spring Security UserDetails implementation
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority(it) }.toMutableList()
    }
    
    override fun getUsername(): String = email
    
    override fun getPassword(): String = passwordHash
    
    // Account status methods (customize as needed)
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}