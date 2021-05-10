package com.littlecorgi.courseji.leave.service.impl

import com.littlecorgi.courseji.checkon.repository.CheckOnRepository
import com.littlecorgi.courseji.classDetail.repository.ClassRepository
import com.littlecorgi.courseji.common.utils.JPushUtil
import com.littlecorgi.courseji.leave.exception.LeaveInfoInvalidException
import com.littlecorgi.courseji.leave.exception.LeaveNotFoundException
import com.littlecorgi.courseji.leave.model.Leave
import com.littlecorgi.courseji.leave.repository.LeaveRepository
import com.littlecorgi.courseji.leave.service.LeaveService
import com.littlecorgi.courseji.student.exception.StudentNotFoundException
import com.littlecorgi.courseji.student.repository.StudentRepository
import com.littlecorgi.courseji.teacher.exception.TeacherNotFoundException
import com.littlecorgi.courseji.teacher.repository.TeacherRepository
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
    private lateinit var classRepository: ClassRepository

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    @Autowired
    private lateinit var checkOnRepository: CheckOnRepository

    private val logger = LoggerFactory.getLogger(javaClass)

    /****************
     * 继承方法
     ***************/
    override fun createLeave(studentId: Long, classId: Long, leave: Leave): Leave {
        val student = studentRepository.findById(studentId).orElseThrow { StudentNotFoundException() }
        val theClass = classRepository.findById(classId).orElseThrow { ClassNotFoundException() }

        leave.apply {
            this.states = 0
            if (this.title.isEmpty()) {
                throw LeaveInfoInvalidException("标题不能为空")
            }
            if (this.description.isEmpty()) {
                throw LeaveInfoInvalidException("描述不能为空")
            }
            this.student = student
            this.classDetail = theClass
        }

        // 当创建请假时通知对应教师
        JPushUtil.sendTeacherPush("教师${theClass.teacher.id}", "学生：${student.name} 发起了一个请假，请及时审批", "有新的请假申请")
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
        if (approvalState !in 1..2) throw LeaveInfoInvalidException("教师审批状态必须是1(同意)或者2(拒绝)")
        val leave = leaveRepository.findById(leaveId).orElseThrow { LeaveNotFoundException() }
        leave.apply {
            id = leaveId // 其实这块没必要多此一举，但是。。。写一下吧
            states = approvalState
            opinion = approval
        }
        val leaveInfo = leaveRepository.save(leave)
        // todo 完善 教师批准请假自动将签到转为请假状态 （目前的逻辑会StackOverflow或者OutOfMemory，进而程序崩溃）
        // if (leaveInfo.states == 1) {
        //     // 如果教师批准请假，则还需要从学生的签到列表里面看看有没有时间有交集的签到，如果有，将状态改为请假
        //     if (leave.student.checkOnList != null) {
        //         for (checkOn in leave.student.checkOnList!!) {
        //             if (checkOn.attendance.classDetail == leave.classDetail) {
        //                 val leaveTime = leave.startTime..leave.endTime
        //                 val attendanceTime = checkOn.attendance.startTime..checkOn.attendance.endTime
        //                 if (leaveTime.intersect(attendanceTime).isNotEmpty()) {
        //                     if (checkOn.checkOnStates == 0) {
        //                         checkOn.checkOnStates = 2
        //                         checkOnRepository.save(checkOn)
        //                     }
        //                 }
        //             }
        //         }
        //     }
        // }

        // 教师审批完请假后通知学生
        JPushUtil.sendStudentPush("学生${leave.student.id}", "请假：\"${leave.title}\" 审批状态更新啦，请查看", "请假审批状态更新啦")
        return leaveInfo
    }

    override fun getLeaveFromStudent(studentId: Long): List<Leave> {
        val student = studentRepository.findById(studentId).orElseThrow { StudentNotFoundException() }
        return leaveRepository.findAllByStudent(student)
    }

    override fun getLeaveFromTeacher(teacherId: Long): List<Leave> {
        val teacher = teacherRepository.findById(teacherId).orElseThrow { TeacherNotFoundException() }
        if (teacher.classList == null) {
            return emptyList()
        }
        val allLeaveList = ArrayList<Leave>()
        for (theClass in teacher.classList!!) {
            theClass.leaveList?.let { allLeaveList.addAll(it) }
        }
        return allLeaveList
    }
}
