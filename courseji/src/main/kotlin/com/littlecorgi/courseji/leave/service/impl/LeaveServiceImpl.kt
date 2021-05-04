package com.littlecorgi.courseji.leave.service.impl

import com.littlecorgi.courseji.`class`.ClassRepository
import com.littlecorgi.courseji.leave.exception.LeaveInfoInvalidException
import com.littlecorgi.courseji.leave.exception.LeaveNotFoundException
import com.littlecorgi.courseji.leave.model.Leave
import com.littlecorgi.courseji.leave.repository.LeaveRepository
import com.littlecorgi.courseji.leave.service.LeaveService
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
    private lateinit var classRepository: ClassRepository

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
