package com.huisheng.kotlinsecurity.model.vo

import com.huisheng.kotlinsecurity.enums.ResultCode

class ResultVO<T>(resultCode: ResultCode, data: T) {
    private var code = 0
    private var msg: String? = null
    private var data: Any? = null

    init {
        this.code = resultCode.code
        this.msg = resultCode.msg
        this.data = data
    }

    constructor(data: T) : this(ResultCode.SUCCESS, data){
        this.code = ResultCode.SUCCESS.code
        this.msg = ResultCode.SUCCESS.msg
        this.data = data
    }

    override fun toString(): String {
        return java.lang.String.format("{\"code\":%d,\"msg\":\"%s\",\"data\":\"%s\"}", code, msg, data.toString())
    }
}