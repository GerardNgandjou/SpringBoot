package com.example.testsecurity.service

import org.springframework.stereotype.Service
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.beans.factory.annotation.Value
import java.util.Date
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.Claims
import io.jsonwebtoken.security.Keys
import javax.crypto.SecretKey

@Service
class JwtService {
    
    @Value("\${jwt.secret}")  // Injected from application.yml
    private lateinit var secretKey: String
    
    @Value("\${jwt.expiration}")  // JWT expiration time
    private var expiration: Long = 86400000
    
    // Generate JWT token for a user
    fun generateToken(username: String): String {
        val now = Date()
        val expiryDate = Date(now.time + expiration)
        
        return Jwts.builder()
            .setSubject(username)  // Set username as subject
            .setIssuedAt(now)  // Token creation time
            .setExpiration(expiryDate)  // Token expiration time
            .setIssuer("auth-service")  // Token issuer
            .signWith(getSigningKey())  // Sign with secret key
            .compact()
    }
    
    // Extract username from JWT token
    fun extractUsername(token: String): String {
        return extractAllClaims(token).subject
    }
    
    // Validate JWT token
    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }
    
    // Check if token is expired
    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }
    
    // Extract expiration date from token
    private fun extractExpiration(token: String): Date {
        return extractAllClaims(token).expiration
    }
    
    // Extract all claims from token
    private fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .body
    }
    
    // Get signing key from secret
    private fun getSigningKey(): SecretKey {
        return Keys.hmacShaKeyFor(secretKey.toByteArray())
    }
}