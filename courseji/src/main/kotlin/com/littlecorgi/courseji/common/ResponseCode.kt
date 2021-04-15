package com.littlecorgi.courseji.common

/**
 * 响应状态码
 *
 * @author littlecorgi
 * @date 2021/4/14
 */
enum class ResponseCode(val code: Int, val msg: String) {

    // 成功
    SUCCESS(200, "成功."),

    // 失败
    FAILURE(800, "失败!"),

    // 密码错误
    PASSWORD_ERROR(801, "密码错误!"),

    // 用户已经存在
    USER_HAS_EXIST(802, "用户已经存在!"),

    // 该用户不存在
    NO_USER(803, "用户不存在!"),

    // 用户信息存在问题
    USER_INFO_INVALID(804, "用户信息错误!")
}