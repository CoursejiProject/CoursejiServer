package com.littlecorgi.courseji.schedule.service

import com.littlecorgi.courseji.schedule.model.Schedule

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
     */
    fun joinCourse(studentId: Long, courseId: Long): Schedule
}
