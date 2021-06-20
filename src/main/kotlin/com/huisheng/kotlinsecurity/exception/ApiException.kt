package com.huisheng.kotlinsecurity.exception

import com.huisheng.kotlinsecurity.enums.ResultCode

class ApiException(
    private var msg: String = "",
    private var resultCode: ResultCode
) : RuntimeException() {

    init {
        this.msg = msg
        this.resultCode = resultCode
    }

    constructor() : this(ResultCode.FAILED)

    constructor(resultCode: ResultCode) : this(resultCode.msg, resultCode)

    constructor(msg: String) : this(msg, ResultCode.FAILED)
}