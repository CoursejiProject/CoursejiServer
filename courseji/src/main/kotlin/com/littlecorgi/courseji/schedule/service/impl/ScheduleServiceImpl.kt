package com.littlecorgi.courseji.schedule.service.impl

import com.littlecorgi.courseji.course.exception.CourseNotFoundException
import com.littlecorgi.courseji.course.repository.CourseRepository
import com.littlecorgi.courseji.schedule.exception.StudentAlreadyJoinedException
import com.littlecorgi.courseji.schedule.model.Schedule
import com.littlecorgi.courseji.schedule.repository.ScheduleRepository
import com.littlecorgi.courseji.schedule.service.ScheduleService
import com.littlecorgi.courseji.student.exception.StudentNotFoundException
import com.littlecorgi.courseji.student.repository.StudentRepository
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * [com.littlecorgi.courseji.schedule.service.ScheduleService]的实现类
 *
 * @author littlecorgi
 * @date 2021/4/19
 */
@Slf4j
@Service
class ScheduleServiceImpl : ScheduleService {

    @Autowired
    private lateinit var scheduleRepository: ScheduleRepository

    @Autowired
    private lateinit var courseRepository: CourseRepository

    @Autowired
    private lateinit var studentRepository: StudentRepository

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun joinCourse(studentId: Long, courseId: Long): Schedule {
        if (!courseRepository.existsById(courseId)) {
            throw CourseNotFoundException()
        }
        if (!studentRepository.existsById(studentId)) {
            throw StudentNotFoundException()
        }
        val student = studentRepository.findById(studentId).get()
        val course = courseRepository.findById(courseId).get()
        if (!scheduleRepository.existsByStudentAndCourse(student, course)) {
            throw StudentAlreadyJoinedException()
        }
        return scheduleRepository.save(Schedule(student, course))
    }
}
