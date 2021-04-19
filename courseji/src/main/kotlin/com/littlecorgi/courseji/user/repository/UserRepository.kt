package com.littlecorgi.courseji.user.repository

import com.littlecorgi.courseji.user.model.User
import org.springframework.data.repository.CrudRepository
import java.util.*

/**
 * 将会由Spring自动实现
 *
 * @author littlecorgi
 * @date 2021/4/13
 */
interface UserRepository : CrudRepository<User, Long> {

    /**
     * 根据email和password获取user对象
     */
    fun findByEmailAndPassword(email: String, password: String): Optional<User>

    /**
     * 根据email获取user对象
     */
    fun findByEmail(email: String): Optional<User>

    /**
     * 根据email判断用户是否已经存在
     */
    fun existsUserByEmail(email: String): Boolean
}