package com.example.login1.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

@Service
class JWTService {

    // Secret key used to sign JWTs (Base64-encoded)
    private var secretKey: String

    init {
        try {
            // Generate a secure HMAC-SHA256 key
            val keyGen = KeyGenerator.getInstance("HmacSHA256")
            val sk = keyGen.generateKey()

            // Encode the key to Base64 so it can be stored as a String
            secretKey = Base64.getEncoder().encodeToString(sk.encoded)

        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to generate JWT secret key", e)
        }
    }

    // Generate a JWT token for a given username
    fun generateToken(username: String): String {

        // Optional extra claims (can be roles, permissions, etc.)
        val claims: MutableMap<String, Any> = HashMap()

        return Jwts.builder()
            .claims()
            .add(claims)              // Add custom claims
            .subject(username)        // Set the username as the subject
            .issuedAt(Date(System.currentTimeMillis())) // Token creation time

            // Token expiration (30 hours)
            .expiration(
//                Date(System.currentTimeMillis() + 1000 * 60 * 60 * 30)
                Date(System.currentTimeMillis() + 60 * 60 * 30)
            )

            .and()
            .signWith(getKey())       // Sign the token using the secret key
            .compact()                // Build the JWT
    }

    // Decode the Base64 secret key and return a SecretKey for signing
    private fun getKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }


    fun extractUserName(token: String): String {
        return extractClaim(token) { it.subject }
    }

    private fun <T> extractClaim(
        token: String,
        claimResolver: (Claims) -> T
    ): T {
        val claims = extractAllClaims(token)
        return claimResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(getKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }


    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val userName = extractUserName(token)
        return userName == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token) { it.expiration }
    }


}