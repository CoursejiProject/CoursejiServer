package com.littlecorgi.courseji.attendance.service

import com.littlecorgi.courseji.attendance.exception.AttendanceInfoInvalidException
import com.littlecorgi.courseji.attendance.model.Attendance
import com.littlecorgi.courseji.course.exception.CourseNotFoundException

/**
 *
 * @author littlecorgi
 * @date 2021/4/21
 */
interface AttendanceService {

    /**
     * 创建一个新的签到。并在创建时将所有学生的签到纪录存放到CheckOn表中，并设置为未签到
     *
     * @param classId 课程ID [com.littlecorgi.courseji.`class`.model.Class#id]
     * @param attendanceInfo 签到信息 [com.littlecorgi.courseji.attendance.model.Attendance]
     * @return 创建好的attendance [com.littlecorgi.courseji.attendance.model.Attendance]
     * @throws AttendanceInfoInvalidException 考勤信息不合法时抛出此异常
     * @throws CourseNotFoundException 根据courseId查询不到课程信息时抛出此异常
     * @throws [cn.jiguang.common.resp.APIConnectionException] 极光推送连接异常，可以稍后重试
     * @throws [cn.jiguang.common.resp.APIRequestException] 极光推送返回的异常信息，具体信息可以查看极光推送官方文档
     */
    fun createNewAttendance(
        classId: Long,
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

    /**
     * 根据教师获取所有考勤
     *
     * @param teacherId 教师id
     * @throws TeacherNotFoundException 根据teacherId查询不到课程信息时抛出此异常
     */
    abstract fun getAllAttendanceFromTeacher(teacherId: Long): List<Attendance>
}
