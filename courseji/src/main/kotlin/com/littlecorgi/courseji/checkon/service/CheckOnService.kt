package com.littlecorgi.courseji.checkon.service

import com.littlecorgi.courseji.attendance.exception.AttendanceNotFoundException
import com.littlecorgi.courseji.attendance.exception.ClassNoAttendanceException
import com.littlecorgi.courseji.checkon.exception.CheckOnNotFoundException
import com.littlecorgi.courseji.checkon.model.CheckOn
import com.littlecorgi.courseji.course.exception.CourseNotFoundException
import com.littlecorgi.courseji.student.exception.StudentNotFoundException

/**
 * 签到纪录的Service，由[com.littlecorgi.courseji.checkon.service.impl.CheckOnServiceImpl]实现
 *
 * @author littlecorgi
 * @date 2021/4/22
 */
interface CheckOnService {

    /**
     * 参与签到
     *
     * 这块的逻辑是根据学生id[com.littlecorgi.courseji.student.model.Student.id]和考勤id[com.littlecorgi.courseji.attendance.model.Attendance.id]
     *  去查询对应的CheckOn纪录，并将这个纪录的checkOnStates修改为true，并添加位置等其他信息。(因为在教师创建签到时，会默认创建所有学生的CheckOn信息，不过checkOnStates为false。
     *
     * @param studentId 学生id [com.littlecorgi.courseji.student.model.Student.id]
     * @param attendanceId 考勤id [com.littlecorgi.courseji.attendance.model.Attendance.id]
     * @param checkOnInfo 签到信息，主要是位置信息 [com.littlecorgi.courseji.checkon.model.CheckOn]
     * @throws StudentNotFoundException 当根据studentId找不到Student数据时抛出此异常
     * @throws AttendanceNotFoundException 当根据attendanceId找不到Attendance数据时抛出此异常
     * @throws CheckOnNotFoundException 当根据Student和Attendance找不到CheckOn数据时抛出此异常
     */
    fun checkIn(studentId: Long, attendanceId: Long, checkOnInfo: CheckOn): CheckOn

    /**
     * 获取这名学生在这个班级的所有签到纪录
     *
     * @param studentId 学生id [com.littlecorgi.courseji.student.model.Student.id]
     * @param classId 班级id [com.littlecorgi.courseji.`class`.Class.id]
     * @throws StudentNotFoundException 当根据studentId找不到Student数据时抛出此异常
     * @throws CourseNotFoundException 当根据curseId找不到Course数据时抛出此异常
     * @throws ClassNoAttendanceException 当课程没有考勤信息时就抛出此异常
     */
    fun getTheStudentCheckInInfoForTheClass(studentId: Long, classId: Long): List<CheckOn>

    /**
     * 获取这名学生所有课的签到纪录
     *
     * @param studentId 学生id [com.littlecorgi.courseji.student.model.Student.id]
     * @throws StudentNotFoundException 当根据studentId找不到Student数据时抛出此异常
     */
    fun getTheStudentAllCheckInInfo(studentId: Long): List<CheckOn>

    /**
     * 获取签到时间
     *  实际上获取到的是此纪录最后一次修改时间 [com.littlecorgi.courseji.checkon.model.CheckOn.lastModifiedTime]
     *
     * @param checkOnId 签到纪录id [com.littlecorgi.courseji.checkon.model.CheckOn.id]
     * @throws CheckOnNotFoundException 当根据Student和Attendance找不到CheckOn数据时抛出此异常
     */
    fun getCheckInTime(checkOnId: Long): Long

    /**
     * 获取签到定位
     *
     * @param checkOnId 签到纪录id [com.littlecorgi.courseji.checkon.model.CheckOn.id]
     * @return 经纬度信息，[com.littlecorgi.courseji.checkon.model.CheckOn.longitude] [com.littlecorgi.courseji.checkon.model.CheckOn.latitude]
     * @throws CheckOnNotFoundException 当根据Student和Attendance找不到CheckOn数据时抛出此异常
     */
    fun getCheckInLocation(checkOnId: Long): Pair<Float, Float>
}
