package com.huisheng.kotlinsecurity.security

import com.huisheng.kotlinsecurity.enums.ResultCode
import com.huisheng.kotlinsecurity.model.vo.ResultVO
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import java.io.PrintWriter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class MyEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authenticationException: AuthenticationException?
    ) {
        response!!.contentType = "application/json;charset=utf-8"
        val out: PrintWriter = response.writer
        val resultVO: ResultVO<String> = ResultVO(ResultCode.UNAUTHORIZED, "没有登录")
        out.write(resultVO.toString())
        out.flush()
        out.close()
    }
}