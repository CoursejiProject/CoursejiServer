package com.littlecorgi.courseji.classandstudent

import com.littlecorgi.courseji.`class`.Class
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 * @author littlecorgi
 * @date 2021/5/4
 */
interface ClassAndStudentRepository : JpaRepository<ClassAndStudent, Long> {

    /**
     * 根据classDetail获取这个班级对应的所有的学生
     */
    fun findAllByClassDetail(classDetail: Class): List<ClassAndStudent>
}