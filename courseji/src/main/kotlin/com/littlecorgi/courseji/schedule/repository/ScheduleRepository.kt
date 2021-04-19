package com.littlecorgi.courseji.schedule.repository

import com.littlecorgi.courseji.course.model.Course
import com.littlecorgi.courseji.schedule.model.Schedule
import com.littlecorgi.courseji.student.model.Student
import org.springframework.data.repository.CrudRepository

/**
 *
 * @author littlecorgi
 * @date 2021/4/19
 */
interface ScheduleRepository : CrudRepository<Schedule, Long> {

    /**
     * 根据Course和Student对象查询是否存在对象
     *
     * @param student [com.littlecorgi.courseji.student.model.Student]对象
     * @param course [com.littlecorgi.courseji.course.model.Course]对象
     * @return 如果存在，返回true
     */
    fun existsByStudentAndCourse(student: Student, course: Course): Boolean
}
