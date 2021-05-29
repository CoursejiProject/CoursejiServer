package com.littlecorgi.courseji.attendance.service.impl

import com.littlecorgi.courseji.attendance.exception.AttendanceInfoInvalidException
import com.littlecorgi.courseji.attendance.model.Attendance
import com.littlecorgi.courseji.attendance.repository.AttendanceRepository
import com.littlecorgi.courseji.attendance.service.AttendanceService
import com.littlecorgi.courseji.checkon.model.CheckOn
import com.littlecorgi.courseji.checkon.repository.CheckOnRepository
import com.littlecorgi.courseji.classDetail.repository.ClassRepository
import com.littlecorgi.courseji.common.utils.JPushUtil
import com.littlecorgi.courseji.teacher.exception.TeacherNotFoundException
import com.littlecorgi.courseji.teacher.repository.TeacherRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 *
 * @author littlecorgi
 * @date 2021/4/21
 */
@Service
class AttendanceServiceImpl : AttendanceService {

    @Autowired
    private lateinit var attendanceRepository: AttendanceRepository

    @Autowired
    private lateinit var checkOnRepository: CheckOnRepository

    @Autowired
    private lateinit var classRepository: ClassRepository

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    override fun createNewAttendance(classId: Long, attendanceInfo: Attendance): Attendance {
        verifyInfoValid(attendanceInfo)
        val theClass = classRepository.findById(classId).orElseThrow { ClassNotFoundException() }
        attendanceInfo.classDetail = theClass
        // 创建签到时，顺便将所有学生的签到信息添加到CheckOn里去
        val attendance = attendanceRepository.save(attendanceInfo)
        for (classAndStudent in attendance.classDetail.classAndStudentList!!) {
            // 都直接使用默认参数
            val checkOn = CheckOn()
            checkOn.student = classAndStudent.student
            checkOn.checkOnStates = 0
            checkOn.attendance = attendance
            checkOnRepository.save(checkOn)

            // 当发起签到时通知每一个学生
            JPushUtil.sendStudentPush("学生${checkOn.student.id}", "课程《${theClass.name}》发起一个签到，请及时签到", "有签到啦")
            JPushUtil.sendStudentCustomMessage("学生${checkOn.student.id}", "课程《${theClass.name}》发起一个签到，请及时签到", "有签到啦")
        }
        return attendance
    }

    override fun updateAttendanceInfo(attendanceId: Long, attendance: Attendance): Attendance {
        verifyInfoValid(attendance)
        attendance.id = attendanceId
        return attendanceRepository.save(attendance)
    }

    override fun getAllAttendanceFromTeacher(teacherId: Long): List<Attendance> {
        val teacher = teacherRepository.findById(teacherId).orElseThrow { TeacherNotFoundException() }
        if (teacher.classList == null) {
            return emptyList()
        }
        val list = ArrayList<Attendance>()
        for (a in teacher.classList!!) {
            if (a.attendanceList != null) {
                list.addAll(a.attendanceList!!)
            }
        }
        return list.sortedByDescending { it.createdTime }
    }

    /*********************
     * 私有方法
     ********************/
    private fun verifyInfoValid(attendance: Attendance) =
        attendance.apply {
            if (title.isEmpty()) {
                throw AttendanceInfoInvalidException("签到标题不能为空")
            }
            if (description.isEmpty()) {
                throw AttendanceInfoInvalidException("签到描述信息不能为空")
            }
            if (startTime == 0L) {
                throw AttendanceInfoInvalidException("签到开始时间不能为空")
            }
            if (endTime == 0L) {
                throw AttendanceInfoInvalidException("签到结束时间不能为空")
            }
        }
}
