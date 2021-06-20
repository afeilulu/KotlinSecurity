package com.huisheng.kotlinsecurity.repository

import com.huisheng.kotlinsecurity.entity.UserAccount
import com.huisheng.kotlinsecurity.entity.UserRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserAccount, Long>, QuerydslPredicateExecutor<UserAccount> {
    fun findOneByUsername(username: String): UserAccount?
}

@Repository
interface RoleRepository : JpaRepository<UserRole, Long>, QuerydslPredicateExecutor<UserRole> {
    fun findByRole(role: String): UserRole?
}