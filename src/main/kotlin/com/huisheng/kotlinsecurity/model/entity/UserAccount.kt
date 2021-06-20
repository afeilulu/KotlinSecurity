package com.huisheng.kotlinsecurity.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.Data
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import javax.persistence.*


@Entity
@Data
class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(unique = true)
    var username: String = ""

    @JsonIgnore
    var password: String = ""
        set(value) {
            field = BCryptPasswordEncoder().encode(value)
        }

    var active = true

    @OneToMany @JoinColumn(name = "user_role_id")
    var userRoles: List<UserRole>? = null

    constructor(username: String, password: String, userRoles: List<UserRole>?, active: Boolean){
        this.username = username
        this.password = password
        this.userRoles = userRoles
        this.active = active
    }

}
