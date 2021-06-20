package com.huisheng.kotlinsecurity.security

import io.jsonwebtoken.Claims
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class LoginFilter(
    private val jwtManager: JwtManager,
    private val userService: CustomDBUserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        var claims: Claims? = null
        // 从请求头中获取token字符串并解析
        if (request.getHeader("Authorization") != null ){
            claims = jwtManager.parse(request.getHeader("Authorization").replaceFirst("Bearer ",""))
        }
        if (claims != null) {
            val username: String = claims.subject
            val user: UserDetails = userService.loadUserByUsername(username)
            val authentication: Authentication =
                UsernamePasswordAuthenticationToken(user, user.password, user.authorities)
            SecurityContextHolder.getContext().authentication = authentication
        }
        chain.doFilter(request, response)
    }
}