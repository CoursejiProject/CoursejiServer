package com.littlecorgi.courseji.attendance.service.impl

import com.littlecorgi.courseji.classDetail.repository.ClassRepository
import com.littlecorgi.courseji.attendance.exception.AttendanceInfoInvalidException
import com.littlecorgi.courseji.attendance.model.Attendance
import com.littlecorgi.courseji.attendance.repository.AttendanceRepository
import com.littlecorgi.courseji.attendance.service.AttendanceService
import com.littlecorgi.courseji.checkon.model.CheckOn
import com.littlecorgi.courseji.checkon.repository.CheckOnRepository
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
        }
        return attendance
    }

    override fun updateAttendanceInfo(attendanceId: Long, attendance: Attendance): Attendance {
        verifyInfoValid(attendance)
        attendance.id = attendanceId
        return attendanceRepository.save(attendance)
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
