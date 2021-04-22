package com.littlecorgi.courseji.attendance.service.impl

import com.littlecorgi.courseji.attendance.exception.AttendanceInfoInvalidException
import com.littlecorgi.courseji.attendance.model.Attendance
import com.littlecorgi.courseji.attendance.repository.AttendanceRepository
import com.littlecorgi.courseji.attendance.service.AttendanceService
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

    override fun createNewAttendance(teacherId: Long, courseId: Long, attendance: Attendance): Attendance {
        verifyInfoValid(attendance)
        return attendanceRepository.save(attendance)
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
