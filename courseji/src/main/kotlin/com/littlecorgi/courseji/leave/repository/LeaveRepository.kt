package com.littlecorgi.courseji.leave.repository

import com.littlecorgi.courseji.`class`.Class
import com.littlecorgi.courseji.leave.model.Leave
import com.littlecorgi.courseji.student.model.Student
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 * @author littlecorgi
 * @date 2021/4/24
 */
interface LeaveRepository : JpaRepository<Leave, Long> {

    /**
     * 根据student获取所有的leave
     */
    fun findAllByStudent(student: Student): List<Leave>

    /**
     * 根据student和classDetail找到审批状态为已审批
     */
    fun findAllByStudentAndClassDetailAndStates(student: Student, classDetail: Class, states: Int): List<Leave>
}
