package com.littlecorgi.courseji.checkon.service.impl

import com.littlecorgi.courseji.attendance.exception.AttendanceNotFoundException
import com.littlecorgi.courseji.attendance.exception.CourseNoAttendanceException
import com.littlecorgi.courseji.attendance.repository.AttendanceRepository
import com.littlecorgi.courseji.checkon.exception.CheckOnNotFoundException
import com.littlecorgi.courseji.checkon.model.CheckOn
import com.littlecorgi.courseji.checkon.repository.CheckOnRepository
import com.littlecorgi.courseji.checkon.service.CheckOnService
import com.littlecorgi.courseji.course.exception.CourseNotFoundException
import com.littlecorgi.courseji.course.repository.CourseRepository
import com.littlecorgi.courseji.student.exception.StudentNotFoundException
import com.littlecorgi.courseji.student.repository.StudentRepository
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * CheckOnService的实现类
 *
 * @author littlecorgi
 * @date 2021/4/22
 */
@Slf4j
@Service
class CheckOnServiceImpl : CheckOnService {

    @Autowired
    private lateinit var checkOnRepository: CheckOnRepository

    @Autowired
    private lateinit var studentRepository: StudentRepository

    @Autowired
    private lateinit var attendanceRepository: AttendanceRepository

    @Autowired
    private lateinit var courseRepository: CourseRepository

    private val logger = LoggerFactory.getLogger(javaClass)

    /**************************
     * 重写方法
     *************************/

    override fun checkIn(studentId: Long, attendanceId: Long, checkOnInfo: CheckOn): CheckOn {
        val student = studentRepository.findById(studentId).orElseThrow { StudentNotFoundException() }
        val attendance = attendanceRepository.findById(attendanceId).orElseThrow { AttendanceNotFoundException() }
        val checkOn = checkOnRepository.findByStudentAndAttendance(student, attendance)
            .orElseThrow { CheckOnNotFoundException() }.also {
                it.checkOnStates = 1
                with(checkOnInfo) {
                    it.latitude = latitude
                    it.longitude = longitude
                }
            }
        return checkOnRepository.save(checkOn)
    }

    override fun getTheStudentCheckInInfoForTheClass(studentId: Long, courseId: Long): List<CheckOn> {
        val course = courseRepository.findById(courseId).orElseThrow { CourseNotFoundException() }
        val student = studentRepository.findById(studentId).orElseThrow { StudentNotFoundException() }
        val attendanceList = course.attendanceList
        if (attendanceList.isEmpty()) {
            throw CourseNoAttendanceException()
        }
        val checkOnList = ArrayList<CheckOn>()
        for (attendance in attendanceList) {
            checkOnList.addAll(attendance.checkOnList)
        }
        return checkOnList.filter {
            it.student == student
        }
    }

    override fun getTheStudentAllCheckInInfo(studentId: Long): List<CheckOn> {
        val student = studentRepository.findById(studentId).orElseThrow { StudentNotFoundException() }
        return student.checkOnList
    }

    override fun getCheckInTime(checkOnId: Long): Long {
        val checkOn = checkOnRepository.findById(checkOnId).orElseThrow { CheckOnNotFoundException() }
        return checkOn.lastModifiedTime
    }

    override fun getCheckInLocation(checkOnId: Long): Pair<Float, Float> {
        val checkOn = checkOnRepository.findById(checkOnId).orElseThrow { CheckOnNotFoundException() }
        return Pair(checkOn.longitude, checkOn.latitude)
    }
}