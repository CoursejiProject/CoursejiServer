package com.littlecorgi.courseji.classDetail.repository

import com.littlecorgi.courseji.classDetail.model.Class
import com.littlecorgi.courseji.teacher.model.Teacher
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 * @author littlecorgi
 * @date 2021/5/4
 */
interface ClassRepository : JpaRepository<Class, Long> {

    /**
     * 根据teacher找到所有的班级
     */
    fun findAllByTeacherOrderById(teacher: Teacher): List<Class>
}