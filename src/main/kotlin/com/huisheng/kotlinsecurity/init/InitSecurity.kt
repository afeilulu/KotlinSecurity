package com.huisheng.kotlinsecurity.init

import com.huisheng.kotlinsecurity.entity.UserAccount
import com.huisheng.kotlinsecurity.entity.UserRole
import com.huisheng.kotlinsecurity.repository.RoleRepository
import com.huisheng.kotlinsecurity.repository.UserRepository
import com.huisheng.kotlinsecurity.security.CustomUserDetails
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.core.Ordered
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.stereotype.Component

@Component
class InitSecurity : CommandLineRunner, Ordered {
    private val logger = KotlinLogging.logger { }

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    override fun run(vararg args: String?) {
        var roleAdmin = roleRepository.findByRole("ROLE_ADMIN")
        if (roleAdmin == null) {
            roleAdmin = roleRepository.save(UserRole("ROLE_ADMIN"))
        }
        var role = roleRepository.findByRole("ROLE_SCHOOL")
        if (role == null) {
            roleRepository.save(UserRole("ROLE_SCHOOL"))
        }
        role = roleRepository.findByRole("ROLE_TEACHER")
        if (role == null) {
            roleRepository.save(UserRole("ROLE_TEACHER"))
        }
        role = roleRepository.findByRole("ROLE_STUDENT")
        if (role == null) {
            roleRepository.save(UserRole("ROLE_STUDENT"))
        }

        var userAdmin = userRepository.findOneByUsername("admin")
        if (userAdmin == null) {
            userAdmin = UserAccount("admin","password",null,true)
            userAdmin = userRepository.save(userAdmin)
        }
        if (userAdmin.userRoles == null) {
            userAdmin.userRoles = listOf(roleAdmin)
            userRepository.save(userAdmin)
            User.withUserDetails(CustomUserDetails(userAdmin)).roles("ADMIN")
        }


    }

    override fun getOrder(): Int {
        return 100
    }
}