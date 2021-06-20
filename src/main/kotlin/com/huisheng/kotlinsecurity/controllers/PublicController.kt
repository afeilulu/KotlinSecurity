package com.huisheng.kotlinsecurity.controllers

import com.huisheng.kotlinsecurity.dtos.RegisterDTO
import com.huisheng.kotlinsecurity.entity.UserAccount
import com.huisheng.kotlinsecurity.repository.RoleRepository
import com.huisheng.kotlinsecurity.repository.UserRepository
import io.swagger.annotations.ApiModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/public")
@ApiModel("public")
class PublicController(private val userRepository: UserRepository,private val roleRepository: RoleRepository) {

    @PostMapping("/register")
    fun register(@RequestBody body: RegisterDTO): ResponseEntity<UserAccount> {
        return ResponseEntity.ok(userRepository.save(UserAccount(body.name,body.pw, listOf(roleRepository.findByRole("ROLE_STUDENT")!!),true)))
    }

}