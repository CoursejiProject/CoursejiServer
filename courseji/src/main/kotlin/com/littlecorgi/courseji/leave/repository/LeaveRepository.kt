package com.littlecorgi.courseji.leave.repository

import com.littlecorgi.courseji.course.model.Course
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
     * 根据Student和Course判断是否已经存在
     *
     * @param student [com.littlecorgi.courseji.student.model.Student] 学生
     * @param course [com.littlecorgi.courseji.course.model.Course] 课程
     */
    fun existsByStudentAndCourse(student: Student, course: Course): Boolean
}
