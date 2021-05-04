package com.littlecorgi.courseji.leave.service

import com.littlecorgi.courseji.leave.exception.LeaveAlreadyExistException
import com.littlecorgi.courseji.leave.exception.LeaveInfoInvalidException
import com.littlecorgi.courseji.leave.exception.LeaveNotFoundException
import com.littlecorgi.courseji.leave.model.Leave
import com.littlecorgi.courseji.student.exception.StudentNotFoundException

/**
 *
 * @author littlecorgi
 * @date 2021/4/24
 */
interface LeaveService {

    /**
     * 创建新的请假
     *
     * @param studentId [com.littlecorgi.courseji.student.model.Student.id] 学生id
     * @param classId [com.littlecorgi.courseji.`class`.model.Class.id] 班级id
     * @param leave [com.littlecorgi.courseji.leave.model.Leave] 请假信息
     * @return 添加后的Leave信息 [com.littlecorgi.courseji.leave.model.Leave]a
     * @throws StudentNotFoundException 根据studentId找不到数据时抛出此异常
     * @throws ClassNotFoundException 根据classId找不到数据时抛出此异常
     * @throws LeaveAlreadyExistException 根据Student和Course查询请假信息发现已经存在则抛出此异常
     * @throws LeaveInfoInvalidException Leave信息不合法则抛出此异常
     */
    fun createLeave(studentId: Long, classId: Long, leave: Leave): Leave

    /**
     * 删除对应的请假
     *
     * 如果已审批，则不许删除
     *
     * @param leaveId [com.littlecorgi.courseji.leave.model.Leave.id] 请假id
     * @return 结果
     * @throws LeaveNotFoundException 根据leaveId查询时发现没有数据则抛出此异常
     */
    fun deleteLeave(leaveId: Long): String

    /**
     * 根据leaveId查询[com.littlecorgi.courseji.leave.model.Leave]数据
     *
     * @param leaveId [com.littlecorgi.courseji.leave.model.Leave.id] 请假id
     * @return 根据leaveId获取到的Leave信息 [com.littlecorgi.courseji.leave.model.Leave]
     * @throws LeaveNotFoundException 根据leaveId查询不到数据时抛出此异常
     */
    fun getLeave(leaveId: Long): Leave

    /**
     * 审批请假申请
     *
     * 此处记得如果已经存在对应的考勤[com.littlecorgi.courseji.schedule.model.Schedule]，则需更改考勤状态[com.littlecorgi.courseji.checkon.model.CheckOn.checkOnStates]
     *
     * @param leaveId [com.littlecorgi.courseji.leave.model.Leave.id] 请假id
     * @param approvalState 审批状态 [com.littlecorgi.courseji.leave.model.Leave.states]
     * @param approval 审批意见
     * @throws LeaveNotFoundException 根据leaveId查询不到数据时抛出此异常
     * @throws LeaveInfoInvalidException Leave信息不合法则抛出此异常
     */
    fun approvalTheLeave(leaveId: Long, approvalState: Int, approval: String): Leave
}
