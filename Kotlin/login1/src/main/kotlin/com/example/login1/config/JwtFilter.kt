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
    private val jwtService: JWTService,
    val context: ApplicationContext
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
//        TODO("Not yet implemented")

        val authHeader: String? = request.getHeader("Authorization")
        var token: String? = null
        var username: String? = null

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7)
            username = jwtService.extractUserName(token)
        }

        if(username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails =
                context.getBean(MyUserDetailsService::class.java)
                    .loadUserByUsername(username)

            if(token != null && jwtService.validateToken(token, userDetails)) {
                val authToken: UsernamePasswordAuthenticationToken =
                        UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.authorities)
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)

                SecurityContextHolder.getContext().authentication = authToken
            }
        }
        filterChain.doFilter(request, response)
    }

}
