package com.littlecorgi.courseji

import com.littlecorgi.courseji.checkon.repository.CheckOnRepository
import com.littlecorgi.courseji.leave.repository.LeaveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 *
 * @author littlecorgi
 * @date 2021/5/5
 */
@Component
@EnableScheduling
class ScheduleChangeCheckOnState {

    @Autowired
    private lateinit var checkOnRepository: CheckOnRepository

    @Autowired
    private lateinit var leaveRepository: LeaveRepository

    @Scheduled(fixedDelay = 5 * 60 * 1000)
    fun changeCheckOnState() {
        println("=======执行定时任务=========")
        println("将所有未签到的过期签到状态置为签到已过期(3)")
        val list = checkOnRepository.findAllByCheckOnStatesIsAndAttendance_EndTimeBefore(0, System.currentTimeMillis())
        for (checkOn in list) {
            checkOn.checkOnStates = 3
            checkOnRepository.save(checkOn)
        }
        println("=======定时任务完毕=========")
    }
}