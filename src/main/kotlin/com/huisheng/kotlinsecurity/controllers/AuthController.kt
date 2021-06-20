package com.huisheng.kotlinsecurity.controllers

import com.huisheng.kotlinsecurity.exception.ApiException
import com.huisheng.kotlinsecurity.model.dto.LoginDTO
import com.huisheng.kotlinsecurity.model.vo.ResultVO
import com.huisheng.kotlinsecurity.model.vo.UserVO
import com.huisheng.kotlinsecurity.repository.UserRepository
import com.huisheng.kotlinsecurity.security.JwtManager
import io.swagger.annotations.ApiModel
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/auth")
@ApiModel("登录认证")
class AuthController(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtManager: JwtManager
) {

    private val logger = KotlinLogging.logger { }


    @PostMapping("/login")
    fun login(@RequestBody @Validated body: LoginDTO): ResponseEntity<UserVO> {

        // 根据用户名查询出用户实体对象
        val user = userRepository.findOneByUsername(body.username)
        // 若没有查到用户或者密码校验失败则抛出异常
        if (user == null || !passwordEncoder.matches(body.password, user.password)) {
            throw ApiException("账号密码错误")
        }

        val userVO = UserVO(user.username,jwtManager.generate(user.username))
        return ResponseEntity.ok(userVO)
    }


}