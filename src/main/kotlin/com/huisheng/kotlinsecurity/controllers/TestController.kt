package com.huisheng.kotlinsecurity.controllers

import com.huisheng.kotlinsecurity.entity.UserAccount
import com.huisheng.kotlinsecurity.repository.UserRepository
import io.swagger.annotations.Api
import mu.KotlinLogging
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping

import org.springframework.web.bind.annotation.RequestMapping

import org.springframework.web.bind.annotation.RestController
import java.security.Principal


@RestController
@RequestMapping("/test")
@Api(tags = ["test"])
class TestController(private val userRepository: UserRepository) {

    private val logger = KotlinLogging.logger { }

    @RequestMapping("/hello")
    fun hello(principal: Principal): String {
        val userAccount: UserAccount? = userRepository.findOneByUsername(principal.name)
        return "Hello " + userAccount?.username
    }

    @GetMapping("/test")
    fun test(): String? {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        logger.info { authentication.toString() }
        return "认证通过"
    }
}