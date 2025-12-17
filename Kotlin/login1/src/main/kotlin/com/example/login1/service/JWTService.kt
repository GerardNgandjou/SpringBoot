package com.example.login1.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.security.Key
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey


@Service
class JWTService {

    private var secretKey: String? = ""

    init {
        try {
            val keyGen = KeyGenerator.getInstance("HmacSHA256")
            val sk = keyGen.generateKey()
            secretKey = Base64.getEncoder().encodeToString(sk.encoded)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }
    }

    fun generateToken(username: String?): String {
        val claims: MutableMap<String?, Any?> = HashMap<String?, Any?>()

        return Jwts.builder()
            .claims()
            .add(claims)
            .subject(username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() * 60 * 60 * 30))
            .and()
            .signWith(getKey())
            .compact()
    }

    private fun getKey(): Key {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }

}