package com.littlecorgi.courseji.schedule.repository

import com.littlecorgi.courseji.course.model.Course
import com.littlecorgi.courseji.schedule.model.Schedule
import com.littlecorgi.courseji.student.model.Student
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 * @author littlecorgi
 * @date 2021/4/19
 */
interface ScheduleRepository : JpaRepository<Schedule, Long> {

    /**
     * 根据Course和Student对象查询是否存在对象
     *
     * @param student [com.littlecorgi.courseji.student.model.Student]对象
     * @param course [com.littlecorgi.courseji.course.model.Course]对象
     * @return 如果存在，返回true
     */
    fun existsByStudentAndCourse(student: Student, course: Course): Boolean

    /**
     * 根据Student获取加入的所有的课程
     *
     * @param student [com.littlecorgi.courseji.student.model.Student]对象
     */
    fun findAllByStudent(student: Student): List<Schedule>

    /**
     * 根据Course和Student对象返回所有对象
     *
     * @param student [com.littlecorgi.courseji.student.model.Student]对象
     * @param course [com.littlecorgi.courseji.course.model.Course]对象
     */
    fun findAllByStudentAndCourse(student: Student, course: Course): List<Schedule>
}
