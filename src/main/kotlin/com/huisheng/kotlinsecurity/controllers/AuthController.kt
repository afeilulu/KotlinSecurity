package com.huisheng.kotlinsecurity.controllers

import com.huisheng.kotlinsecurity.entity.UserAccount
import com.huisheng.kotlinsecurity.enums.ResultCode
import com.huisheng.kotlinsecurity.exception.ApiException
import com.huisheng.kotlinsecurity.model.dto.LoginDTO
import com.huisheng.kotlinsecurity.model.dto.RegisterDTO
import com.huisheng.kotlinsecurity.model.vo.ResultVO
import com.huisheng.kotlinsecurity.model.vo.UserVO
import com.huisheng.kotlinsecurity.repository.RoleRepository
import com.huisheng.kotlinsecurity.repository.UserRepository
import com.huisheng.kotlinsecurity.security.JwtManager
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("auth")
@Api(tags = ["登录认证"])
class AuthController(
        private val userRepository: UserRepository,
        private val roleRepository: RoleRepository,
        private val passwordEncoder: PasswordEncoder,
        private val jwtManager: JwtManager
) {

    private val logger = KotlinLogging.logger { }

    @Value("\${jwt.token}")
    private val jwtToken: String? = null

    @PostMapping("register")
    @ApiOperation(value = "注册", notes = "")
    fun register(@RequestBody body: RegisterDTO): ResponseEntity<UserAccount> {
        return ResponseEntity.ok(userRepository.save(UserAccount(body.name, body.pw, listOf(roleRepository.findByRole("ROLE_STUDENT")!!), true)))
    }

    @PostMapping("login")
    @ApiOperation(value = "登录", notes = "")
    fun login(@RequestBody @Validated body: LoginDTO, response: HttpServletResponse): ResponseEntity<UserVO> {

        // 根据用户名查询出用户实体对象
        val user = userRepository.findOneByUsername(body.username)
        // 若没有查到用户或者密码校验失败则抛出异常
        if (user == null || !passwordEncoder.matches(body.password, user.password)) {
            throw ApiException("账号密码错误")
        }

        val token = jwtManager.generate(user.username)
        val userVO = UserVO(user.username, token)

        val cookie = Cookie(jwtToken, token)
        logger.info { cookie.maxAge }
        cookie.isHttpOnly = true
        response.addCookie(cookie)

        return ResponseEntity.ok(userVO)
    }

    @PostMapping("logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Any> {
        val cookie = Cookie(jwtToken, "")
        cookie.maxAge = 0
        response.addCookie(cookie)

        return ResponseEntity.ok(ResultVO(ResultCode.SUCCESS))
    }

}