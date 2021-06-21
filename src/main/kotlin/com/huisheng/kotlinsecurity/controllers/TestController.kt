package com.huisheng.kotlinsecurity.controllers

import com.huisheng.kotlinsecurity.repository.UserRepository
import com.huisheng.kotlinsecurity.security.JwtManager
import io.swagger.annotations.Api
import mu.KotlinLogging
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/test")
@Api(tags = ["test"])
class TestController(private val userRepository: UserRepository, private val jwtManager: JwtManager) {

    private val logger = KotlinLogging.logger { }

    @GetMapping("/hello")
    fun test(): String? {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        logger.info { authentication.toString() }
        return "认证通过"
    }

}