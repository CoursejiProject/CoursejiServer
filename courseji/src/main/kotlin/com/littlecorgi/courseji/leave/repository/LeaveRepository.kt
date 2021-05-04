package com.littlecorgi.courseji.leave.repository

import com.littlecorgi.courseji.leave.model.Leave
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 * @author littlecorgi
 * @date 2021/4/24
 */
interface LeaveRepository : JpaRepository<Leave, Long> {
}
