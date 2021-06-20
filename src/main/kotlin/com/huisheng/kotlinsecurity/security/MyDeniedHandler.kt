package com.huisheng.kotlinsecurity.security

import com.huisheng.kotlinsecurity.enums.ResultCode
import com.huisheng.kotlinsecurity.model.vo.ResultVO
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import java.io.PrintWriter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class MyDeniedHandler : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        response!!.contentType = "application/json;charset=utf-8"
        val out: PrintWriter = response.writer
        val resultVO: ResultVO<String> = ResultVO(ResultCode.FORBIDDEN, "没有相关权限")
        out.write(resultVO.toString())
        out.flush()
        out.close()
    }

}