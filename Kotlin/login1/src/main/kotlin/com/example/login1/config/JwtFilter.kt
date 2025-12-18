package com.example.login1.config

import com.example.login1.service.JWTService
import com.example.login1.service.MyUserDetailsService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.ApplicationContext
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
@Component
class JwtFilter(
    // Service responsible for JWT operations (extract username, validate token, etc.)
    private val jwtService: JWTService,

    // Spring application context (used here to get UserDetailsService bean)
    private val context: ApplicationContext
) : OncePerRequestFilter() {

    // This method is executed once per HTTP request
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        // Read the Authorization header from the HTTP request
        val authHeader: String? = request.getHeader("Authorization")

        // Will hold the JWT token extracted from the header
        var token: String? = null

        // Will hold the username extracted from the token
        var username: String? = null

        // Check if the Authorization header exists and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            // Remove "Bearer " prefix to get the actual JWT token
            token = authHeader.substring(7)

            // Extract username (subject) from the JWT token
            username = jwtService.extractUserName(token)
        }

        // Continue only if:
        // 1. A username was successfully extracted
        // 2. The user is not already authenticated in the SecurityContext
        if (username != null && SecurityContextHolder.getContext().authentication == null) {

            // Load user details from the database using UserDetailsService
            val userDetails: UserDetails =
                context.getBean(MyUserDetailsService::class.java)
                    .loadUserByUsername(username)

            // Validate the token against the user details (username + expiration)
            if (token != null && jwtService.validateToken(token, userDetails)) {

                // Create an authentication object recognized by Spring Security
                val authToken = UsernamePasswordAuthenticationToken(
                    userDetails,           // Authenticated user
                    null,                  // No credentials (password not needed)
                    userDetails.authorities // User roles / authorities
                )

                // Attach request details (IP address, session ID, etc.)
                authToken.details =
                    WebAuthenticationDetailsSource().buildDetails(request)

                // Store the authentication in the SecurityContext
                SecurityContextHolder.getContext().authentication = authToken
            }
        }

        // Pass the request to the next filter in the chain
        filterChain.doFilter(request, response)
    }
}
