package com.huisheng.kotlinsecurity.entity

import lombok.Data
import javax.persistence.*


@Entity
@Data
class UserRole(val role: String = "") {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

//    @ManyToOne
//    val userAccount: UserAccount? = null
}