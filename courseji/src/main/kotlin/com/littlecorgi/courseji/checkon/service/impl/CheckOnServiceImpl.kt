package com.littlecorgi.courseji.checkon.service.impl

import com.littlecorgi.courseji.attendance.exception.AttendanceNotFoundException
import com.littlecorgi.courseji.attendance.exception.ClassNoAttendanceException
import com.littlecorgi.courseji.attendance.repository.AttendanceRepository
import com.littlecorgi.courseji.checkon.exception.CheckOnNotFoundException
import com.littlecorgi.courseji.checkon.model.CheckOn
import com.littlecorgi.courseji.checkon.repository.CheckOnRepository
import com.littlecorgi.courseji.checkon.service.CheckOnService
import com.littlecorgi.courseji.classDetail.repository.ClassRepository
import com.littlecorgi.courseji.course.exception.CourseNotFoundException
import com.littlecorgi.courseji.student.exception.StudentNotFoundException
import com.littlecorgi.courseji.student.repository.StudentRepository
import com.littlecorgi.courseji.teacher.exception.TeacherNotFoundException
import com.littlecorgi.courseji.teacher.repository.TeacherRepository
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
    private lateinit var teacherRepository: TeacherRepository

    @Autowired
    private lateinit var attendanceRepository: AttendanceRepository

    @Autowired
    private lateinit var classRepository: ClassRepository

    private val logger = LoggerFactory.getLogger(javaClass)

    /**************************
     * 重写方法
     *************************/

    override fun checkIn(studentId: Long, attendanceId: Long, checkOnInfo: CheckOn): CheckOn {
        val student = studentRepository.findById(studentId).orElseThrow { StudentNotFoundException() }
        val attendance = attendanceRepository.findById(attendanceId).orElseThrow { AttendanceNotFoundException() }
        var checkOn = checkOnRepository.findByStudentAndAttendance(student, attendance)
            .orElseThrow { CheckOnNotFoundException() }.also {
                it.checkOnStates = 1
                with(checkOnInfo) {
                    it.latitude = latitude
                    it.longitude = longitude
                }
            }

        checkOn = checkOnRepository.save(checkOn)
        attendance.checkInNum++
        attendanceRepository.save(attendance)
        return checkOn
    }

    override fun getTheStudentCheckInInfoForTheClass(studentId: Long, classId: Long): List<CheckOn> {
        val theClass = classRepository.findById(classId).orElseThrow { CourseNotFoundException() }
        val student = studentRepository.findById(studentId).orElseThrow { StudentNotFoundException() }
        val attendanceList = theClass.attendanceList
        if (attendanceList!!.isEmpty()) {
            throw ClassNoAttendanceException()
        }
        val checkOnList = ArrayList<CheckOn>()
        for (attendance in attendanceList) {
            checkOnList.addAll(attendance.checkOnList!!)
        }
        return checkOnList.filter {
            it.student == student
        }.sortedByDescending { it.createdTime }
    }

    override fun getTheStudentAllCheckInInfo(studentId: Long): List<CheckOn> {
        val student = studentRepository.findById(studentId).orElseThrow { StudentNotFoundException() }
        return student.checkOnList!!.sortedByDescending { it.createdTime }
    }

    override fun getCheckInTime(checkOnId: Long): Long {
        val checkOn = checkOnRepository.findById(checkOnId).orElseThrow { CheckOnNotFoundException() }
        return checkOn.lastModifiedTime
    }

    override fun getCheckInLocation(checkOnId: Long): Pair<Double, Double> {
        val checkOn = checkOnRepository.findById(checkOnId).orElseThrow { CheckOnNotFoundException() }
        return Pair(checkOn.longitude, checkOn.latitude)
    }

    override fun getAllCheckOnFromTeacher(teacherId: Long): List<CheckOn> {
        val teacher = teacherRepository.findById(teacherId).orElseThrow { TeacherNotFoundException() }
        val checkOnList = ArrayList<CheckOn>()
        if (teacher.classList != null) {
            for (theClass in teacher.classList!!) {
                checkOnList.addAll(checkOnRepository.findAllByAttendance_ClassDetail(theClass))
            }
        }
        return checkOnList.sortedByDescending { it.createdTime }
    }

    override fun getCheckOnFromAttendance(attendanceId: Long): List<CheckOn> {
        val attendance = attendanceRepository.findById(attendanceId).orElseThrow { AttendanceNotFoundException() }
        return checkOnRepository.findAllByAttendance(attendance)
    }
}
