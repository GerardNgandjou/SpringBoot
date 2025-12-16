package com.example.testsecurity.filter

import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import com.example.testsecurity.service.JwtService
import com.example.testsecurity.service.UserDetailsServiceImpl
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.FilterChain

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsServiceImpl
) : OncePerRequestFilter() {
    
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // Skip JWT check for login and register endpoints
        if (shouldNotFilter(request)) {
            filterChain.doFilter(request, response)
            return
        }
        
        val authHeader = request.getHeader("Authorization")
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val jwt = authHeader.substring(7)
            
            try {
                val username = jwtService.extractUsername(jwt)
                
                if (username.isNotEmpty() && SecurityContextHolder.getContext().authentication == null) {
                    val userDetails = userDetailsService.loadUserByUsername(username)
                    
                    if (jwtService.validateToken(jwt, userDetails)) {
                        val authToken = UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.authorities
                        )
                        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                        SecurityContextHolder.getContext().authentication = authToken
                    }
                }
            } catch (e: Exception) {
                logger.error("JWT validation failed: ${e.message}")
            }
        }
        
        filterChain.doFilter(request, response)
    }
    
    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.servletPath
        return path.startsWith("/api/auth/") ||
               path == "/error" ||
               path.startsWith("/swagger-ui/") ||
               path.startsWith("/v3/api-docs/") ||
               path.startsWith("/webjars/")
    }
}