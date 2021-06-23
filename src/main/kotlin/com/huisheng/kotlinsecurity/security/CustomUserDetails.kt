package com.huisheng.kotlinsecurity.security

import com.huisheng.kotlinsecurity.model.entity.UserAccount
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors
import mu.KotlinLogging

open class CustomUserDetails:UserDetails {

    private val logger = KotlinLogging.logger { }

    private val userAccount: UserAccount

    constructor(userAccount: UserAccount){
        this.userAccount = userAccount
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return userAccount.userRoles!!
            .stream()
            .map { userRole ->
                logger.info { "Granting Authority to user with role: ${userRole.role}" }
                SimpleGrantedAuthority(userRole.role)
            }
            .collect(Collectors.toList())
    }

    override fun getPassword(): String {
        return userAccount.password
    }

    override fun getUsername(): String {
        return userAccount.username
    }

    override fun isAccountNonExpired(): Boolean {
        return userAccount.active
    }

    override fun isAccountNonLocked(): Boolean {
        return userAccount.active
    }

    override fun isCredentialsNonExpired(): Boolean {
        return userAccount.active
    }

    override fun isEnabled(): Boolean {
        return userAccount.active
    }

}