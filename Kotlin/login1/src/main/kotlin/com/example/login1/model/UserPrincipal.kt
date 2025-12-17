package com.example.login1.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.Collections
import com.example.login1.model.Users

data class UserPrincipal(
    // val username: String,
    // val password: String,
    // val authorities: List<GrantedAuthority>

    val users: Users 

) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority("ROLE_USER"))
    }

    override fun getPassword(): String {
        return users.password 
    }

    override fun getUsername(): String {
        return users.name
    }

    override fun isAccountNonExpired(): Boolean {
        return true 
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}