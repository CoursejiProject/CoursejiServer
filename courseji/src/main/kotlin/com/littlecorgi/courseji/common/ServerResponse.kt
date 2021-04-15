package com.littlecorgi.courseji.common

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * 服务器响应数据，此类必须是Java
 *
 * @author littlecorgi
 * @date 2021/4/14
 */
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
data class ServerResponse<T> private constructor(
    val status: Int,
    val msg: String,
    val data: T?,
    val errorMsg: String?
) {

    /**
     * 判断是否是成功的状态码
     *
     * @return 是成功的状态码返回true，否则false
     */
    fun success() = status == ResponseCode.SUCCESS.code

    companion object {
        /**
         * 成功响应
         *
         * @param data 成功响应携带的数据
         * @param <T>  携带的数据的类型
         * @return 创建的响应对象
         */
        fun <T> createBySuccess(data: T) =
            ServerResponse(ResponseCode.SUCCESS.code, ResponseCode.SUCCESS.msg, data, null)

        /**
         * 失败响应
         *
         * @param code {@link com.littlecorgi.courseji.common.ResponseCode} 响应失败的状态码
         * @param data 失败响应携带的数据
         * @param errorMsg 当发生错误时携带的Exception信息，可为空
         * @param <T>  携带的数据的类型
         * @return 创建的响应对象
         */
        fun <T> createByFailure(code: ResponseCode, data: T? = null, errorMsg: String? = null) =
            ServerResponse(code.code, code.msg, data, errorMsg)
    }
}
