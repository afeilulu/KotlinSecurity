package com.huisheng.kotlinsecurity.controllers

import com.huisheng.kotlinsecurity.entity.UserAccount
import com.huisheng.kotlinsecurity.repository.UserRepository
import io.swagger.annotations.Api
import mu.KotlinLogging

import org.springframework.web.bind.annotation.RequestMapping

import org.springframework.web.bind.annotation.RestController
import java.security.Principal


@RestController
@RequestMapping("/test")
@Api(tags = ["test"])
class HelloController(private val userRepository: UserRepository) {

    private val logger = KotlinLogging.logger { }

    @RequestMapping("/hello")
    fun hello(principal: Principal): String {
        val userAccount: UserAccount? = userRepository.findOneByUsername(principal.name)
        return "Hello " + userAccount?.username
    }

}