package com.littlecorgi.courseji.schedule.service

import com.littlecorgi.courseji.course.exception.CourseNotFoundException
import com.littlecorgi.courseji.course.model.Course
import com.littlecorgi.courseji.schedule.exception.StudentAlreadyJoinedException
import com.littlecorgi.courseji.schedule.model.Schedule
import com.littlecorgi.courseji.student.exception.StudentNotFoundException

/**
 * Schedule的Service
 *
 * @author littlecorgi
 * @date 2021/4/19
 */
interface ScheduleService {

    /**
     * 加入课程
     *
     * @param studentId 学生id [com.littlecorgi.courseji.student.model.Student#id]
     * @param courseId 课程id [com.littlecorgi.courseji.course.model.Course#id]
     * @return 加入成功后的[com.littlecorgi.courseji.schedule.model.Schedule]对象
     * @throws CourseNotFoundException 根据courseId找不到课程时抛出此异常
     * @throws StudentNotFoundException 根据studentId找不到学生时抛出此异常
     * @throws StudentAlreadyJoinedException 学生已经加入此课程时抛出此异常
     */
    fun joinCourse(studentId: Long, courseId: Long): Schedule

    /**
     * 查询该学生加入的所有课程
     *
     * @param studentId 学生id [com.littlecorgi.courseji.student.model.Student#id]
     * @return 学生加入的所有课程的集合
     * @throws StudentNotFoundException 根据studentId找不到学生时抛出此异常
     */
    fun getAllCourse(studentId: Long): List<Course>

    /**
     * 查询该学生加入的这门课的所有课程（每天都属于一个课）
     *
     * @param studentId 学生id [com.littlecorgi.courseji.student.model.Student#id]
     * @param courseId 课程id [com.littlecorgi.courseji.course.model.Course#id]
     * @throws CourseNotFoundException 根据courseId找不到课程时抛出此异常
     * @throws StudentNotFoundException 根据studentId找不到学生时抛出此异常
     */
    fun getAllCourseFromStudentAndCourse(studentId: Long, courseId: Long): List<Course>
}
