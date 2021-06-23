package com.huisheng.kotlinsecurity.init

import com.huisheng.kotlinsecurity.enums.Role
import com.huisheng.kotlinsecurity.model.entity.QUserAccount
import com.huisheng.kotlinsecurity.model.entity.UserAccount
import com.huisheng.kotlinsecurity.model.entity.UserRole
import com.huisheng.kotlinsecurity.repository.RoleRepository
import com.huisheng.kotlinsecurity.repository.UserRepository
import com.huisheng.kotlinsecurity.security.CustomUserDetails
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.core.Ordered
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component

@Component
class InitSecurity : CommandLineRunner, Ordered {
    private val logger = KotlinLogging.logger { }

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    override fun run(vararg args: String?) {

        if (!userRepository.exists(QUserAccount.userAccount.username.eq("admin"))){
            userRepository.save(UserAccount("admin","password", listOf(roleRepository.save(UserRole(Role.ROLE_ADMIN.name))),true))
        }

    }

    override fun getOrder(): Int {
        return 100
    }
}