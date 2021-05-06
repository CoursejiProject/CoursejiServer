package com.littlecorgi.courseji.classandstudent

import com.littlecorgi.courseji.classDetail.model.Class
import com.littlecorgi.courseji.student.model.Student
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

    /**
     * 根据student找到所有的班级
     */
    fun findAllByStudent(student: Student): List<ClassAndStudent>
}