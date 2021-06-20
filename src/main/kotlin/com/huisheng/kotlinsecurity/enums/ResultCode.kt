package com.huisheng.kotlinsecurity.enums

import lombok.Getter

@Getter
enum class ResultCode(val code: Int, val msg: String) {

    SUCCESS(1000, "操作成功"),

    UNAUTHORIZED(1001, "没有登录"),

    FORBIDDEN(1002, "没有相关权限"),

    VALIDATE_FAILED(1003, "参数校验失败"),

    FAILED(1004, "接口异常"),

    ERROR(5000, "未知错误");

}