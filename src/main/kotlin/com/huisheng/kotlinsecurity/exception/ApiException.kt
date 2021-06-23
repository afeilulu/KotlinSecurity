package com.huisheng.kotlinsecurity.exception

import java.io.Serializable

class ApiException(
    msg: String
) : RuntimeException(msg), Serializable {
    val args: Array<Any>? = null
}