package com.littlecorgi.courseji.leave.service.impl

import com.littlecorgi.courseji.course.exception.CourseNotFoundException
import com.littlecorgi.courseji.course.repository.CourseRepository
import com.littlecorgi.courseji.leave.exception.LeaveAlreadyExistException
import com.littlecorgi.courseji.leave.exception.LeaveInfoInvalidException
import com.littlecorgi.courseji.leave.exception.LeaveNotFoundException
import com.littlecorgi.courseji.leave.model.Leave
import com.littlecorgi.courseji.leave.repository.LeaveRepository
import com.littlecorgi.courseji.leave.service.LeaveService
import com.littlecorgi.courseji.schedule.exception.ScheduleNotFoundException
import com.littlecorgi.courseji.schedule.repository.ScheduleRepository
import com.littlecorgi.courseji.student.exception.StudentNotFoundException
import com.littlecorgi.courseji.student.repository.StudentRepository
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 *
 * @author littlecorgi
 * @date 2021/4/24
 */
@Slf4j
@Service
class LeaveServiceImpl : LeaveService {

    @Autowired
    private lateinit var leaveRepository: LeaveRepository

    @Autowired
    private lateinit var studentRepository: StudentRepository

    @Autowired
    private lateinit var courseRepository: CourseRepository

    @Autowired
    private lateinit var scheduleRepository: ScheduleRepository

    private val logger = LoggerFactory.getLogger(javaClass)

    /****************
     * 继承方法
     ***************/
    override fun createLeave(studentId: Long, courseId: Long, leave: Leave): Leave {
        val student = studentRepository.findById(studentId).orElseThrow { StudentNotFoundException() }
        val course = courseRepository.findById(courseId).orElseThrow { CourseNotFoundException() }
        val schedule =
            scheduleRepository.findByStudentAndCourse(student, course).orElseThrow { ScheduleNotFoundException() }
        if (leaveRepository.existsBySchedule(schedule)) throw LeaveAlreadyExistException()
        leave.apply {
            this.schedule = schedule
            this.states = 0
            if (this.title.isEmpty()) {
                throw LeaveInfoInvalidException("标题不能为空")
            }
            if (this.description.isEmpty()) {
                throw LeaveInfoInvalidException("描述不能为空")
            }
            if (week !in course.startWeek..course.endWeek) {
                throw LeaveInfoInvalidException("请假的周数不在课程上课周数内")
            }
            if (day != schedule.course.day) throw LeaveInfoInvalidException("请假这天此节课并没有上课")
        }
        return leaveRepository.save(leave)
    }

    override fun deleteLeave(leaveId: Long): String {
        if (!leaveRepository.existsById(leaveId)) throw LeaveNotFoundException()
        leaveRepository.deleteById(leaveId)
        return "删除成功。"
    }

    override fun getLeave(leaveId: Long): Leave {
        return leaveRepository.findById(leaveId).orElseThrow { LeaveNotFoundException() }
    }

    override fun approvalTheLeave(leaveId: Long, approvalState: Int, approval: String): Leave {
        if (approvalState !in 0..1) throw LeaveInfoInvalidException("教师审批状态必须是1(同意)或者0(拒绝)")
        val leave = leaveRepository.findById(leaveId).orElseThrow { LeaveNotFoundException() }
        leave.apply {
            id = leaveId // 其实这块没必要多此一举，但是。。。写一下吧
            states = approvalState
            opinion = approval
        }
        return leaveRepository.save(leave)
    }
}
