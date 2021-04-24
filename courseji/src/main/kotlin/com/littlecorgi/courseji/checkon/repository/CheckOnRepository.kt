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
}
