package com.littlecorgi.courseji.user.service

import com.littlecorgi.courseji.user.model.User

/**
 * 用户相关Service
 *
 * @author littlecorgi
 * @date 2021/4/15
 */
interface UserService {
    /**
     * 登录
     *
     * @return 登录后返回User对象
     */
    fun signIn(email: String, password: String): User

    /**
     * 注册
     *
     * @param user 注册需要的用户信息
     * @return 注册结果
     */
    fun signUp(user: User): String

    /**
     * 返回所有用户信息
     *
     * @return 所有用户 {@link kotlin.collections.Iterable} Iterable
     */
    fun getAllUser(): Iterable<User>

    /**
     * 更新密码
     *
     * @param email 邮箱
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    fun updatePassword(email: String, oldPassword: String, newPassword: String): String

    /**
     * 获取用户创建日期
     *
     * @param id 用户id
     * @return 用户创建日期
     */
    fun getCreatedDate(id: Long): Long

    /**
     * 获取用户最后一次修改信息日期
     *
     * @param id 用户id
     * @return 用户最后一次修改信息日期
     */
    fun getLastModifiedDate(id: Long): Long
}
