package com.littlecorgi.courseji.checkon.repository

import com.littlecorgi.courseji.attendance.model.Attendance
import com.littlecorgi.courseji.checkon.model.CheckOn
import com.littlecorgi.courseji.student.model.Student
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

/**
 *
 * @author littlecorgi
 * @date 2021/4/22
 */
interface CheckOnRepository : JpaRepository<CheckOn, Long> {

    /**
     * 根据Student和Attendance获取CheckOn
     *
     * @param student Student对象
     * @param attendance Attendance对象
     */
    fun findByStudentAndAttendance(student: Student, attendance: Attendance): Optional<CheckOn>

    /**
     * 找到结束时间在attendance_endTime之前并且是未签到的CheckOn
     *
     * @param checkOnStates 签到状态
     * @param attendance_endTime 签到的结束时间
     */
    fun findAllByCheckOnStatesIsAndAttendance_EndTimeBefore(checkOnStates: Int, attendance_endTime: Long): List<CheckOn>
}
