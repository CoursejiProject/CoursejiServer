package com.littlecorgi.courseji.teacher.repository

import com.littlecorgi.courseji.teacher.model.Teacher
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

/**
 * 将会由Spring自动实现
 *
 * @author littlecorgi
 * @date 2021/4/13
 */
interface TeacherRepository : JpaRepository<Teacher, Long> {

    /**
     * 根据email和password获取teacher对象
     */
    fun findByEmailAndPassword(email: String, password: String): Optional<Teacher>

    /**
     * 根据email获取teacher对象
     */
    fun findByEmail(email: String): Optional<Teacher>

    /**
     * 根据email判断用户是否已经存在
     */
    fun existsUserByEmail(email: String): Boolean

    /**
     * 根据Phone判断用户是否已经存在
     */
    fun existsByPhone(phone: String): Boolean
}
