package com.littlecorgi.courseji.attendance.service

import com.littlecorgi.courseji.attendance.exception.AttendanceInfoInvalidException
import com.littlecorgi.courseji.attendance.model.Attendance

/**
 *
 * @author littlecorgi
 * @date 2021/4/21
 */
interface AttendanceService {

    /**
     * 创建一个新的签到。并在创建时将所有学生的签到纪录存放到CheckOn表中，并设置为未签到
     *
     * @param teacherId 教师ID [com.littlecorgi.courseji.teacher.model.Teacher#id]
     * @param courseId 课程ID [com.littlecorgi.courseji.course.model.Course#id]
     * @param attendanceInfo 签到信息 [com.littlecorgi.courseji.attendance.model.Attendance]
     * @return 创建好的attendance [com.littlecorgi.courseji.attendance.model.Attendance]
     * @throws AttendanceInfoInvalidException 考勤信息不合法时抛出此异常
     */
    fun createNewAttendance(
        teacherId: Long,
        courseId: Long,
        attendanceInfo: Attendance
    ): Attendance

    /**
     * 更新签到信息
     *
     * @param attendanceId 要修改数据的Attendance数据的主键ID [com.littlecorgi.courseji.attendance.model.Attendance#id]
     * @param attendance 携带要更新的数据的[com.littlecorgi.courseji.attendance.model.Attendance]对象
     * @return 更新后的[com.littlecorgi.courseji.attendance.model.Attendance]
     * @throws AttendanceInfoInvalidException 考勤信息不合法时抛出此异常
     */
    fun updateAttendanceInfo(attendanceId: Long, attendance: Attendance): Attendance
}
