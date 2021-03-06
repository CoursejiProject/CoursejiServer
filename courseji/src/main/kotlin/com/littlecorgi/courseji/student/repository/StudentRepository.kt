package com.littlecorgi.courseji.student.repository

import com.littlecorgi.courseji.student.model.Student
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

/**
 * 将会由Spring自动实现
 *
 * @author littlecorgi
 * @date 2021/4/13
 */
interface StudentRepository : JpaRepository<Student, Long> {

    /**
     * 根据email和password获取user对象
     */
    fun findByEmailAndPassword(email: String, password: String): Optional<Student>

    /**
     * 根据email获取user对象
     */
    fun findByEmail(email: String): Optional<Student>

    /**
     * 根据email判断用户是否已经存在
     */
    fun existsUserByEmail(email: String): Boolean

    /**
     * 根据phone判断用户是否已经存在
     */
    fun existsByPhone(phone: String): Boolean
}
