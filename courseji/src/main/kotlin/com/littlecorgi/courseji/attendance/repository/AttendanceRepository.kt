package com.littlecorgi.courseji.attendance.repository

import com.littlecorgi.courseji.attendance.model.Attendance
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Attendance的Repository
 *
 * @author littlecorgi
 * @date 2021/4/21
 */
interface AttendanceRepository : JpaRepository<Attendance, Long>
