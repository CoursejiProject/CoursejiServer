package com.littlecorgi.courseji.leave.repository

import com.littlecorgi.courseji.leave.model.Leave
import com.littlecorgi.courseji.schedule.model.Schedule
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 * @author littlecorgi
 * @date 2021/4/24
 */
interface LeaveRepository : JpaRepository<Leave, Long> {

    /**
     * 根据schedule判断是否已经存在
     *
     * @param schedule [com.littlecorgi.courseji.schedule.model.Schedule] 学生和课程的对应关系
     */
    fun existsBySchedule(schedule: Schedule): Boolean
}
