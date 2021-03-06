package com.littlecorgi.courseji.leave.controller

import cn.jiguang.common.resp.APIConnectionException
import cn.jiguang.common.resp.APIRequestException
import com.littlecorgi.courseji.common.ResponseCode
import com.littlecorgi.courseji.common.ServerResponse
import com.littlecorgi.courseji.leave.dto.ApprovalStateAndDescriptionDTO
import com.littlecorgi.courseji.leave.exception.LeaveAlreadyExistException
import com.littlecorgi.courseji.leave.exception.LeaveInfoInvalidException
import com.littlecorgi.courseji.leave.exception.LeaveNotFoundException
import com.littlecorgi.courseji.leave.model.Leave
import com.littlecorgi.courseji.leave.service.LeaveService
import com.littlecorgi.courseji.schedule.exception.ScheduleNotFoundException
import com.littlecorgi.courseji.student.exception.StudentNotFoundException
import com.littlecorgi.courseji.teacher.exception.TeacherNotFoundException
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @author littlecorgi
 * @date 2021/4/24
 */
@Api
@Slf4j
@RestController
@RequestMapping(path = ["/leave"])
class LeaveController {

    @Autowired
    private lateinit var leaveService: LeaveService
    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * 创建新的请假
     */
    @ApiOperation(value = "创建新的请假")
    @PostMapping(path = ["/createLeave"])
    fun createLeave(
        @ApiParam(value = "学生id", required = true, example = "0") @RequestParam studentId: Long,
        @ApiParam(value = "班级id", required = true, example = "0") @RequestParam classId: Long,
        @ApiParam(value = "请假信息", required = true) @RequestBody leave: Leave
    ): ServerResponse<Leave> =
        try {
            ServerResponse.createBySuccess(leaveService.createLeave(studentId, classId, leave))
        } catch (e: StudentNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_USER, errorMsg = e.message)
        } catch (e: ClassNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_COURSE, errorMsg = e.message)
        } catch (e: ScheduleNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_SCHEDULE, errorMsg = e.message)
        } catch (e: LeaveAlreadyExistException) {
            ServerResponse.createByFailure(ResponseCode.LEAVE_HAS_EXIST, errorMsg = e.message)
        } catch (e: LeaveInfoInvalidException) {
            ServerResponse.createByFailure(ResponseCode.LEAVE_INFO_INVALID, errorMsg = e.msg)
        } catch (e: APIConnectionException) {
            logger.info("创建新的请假，极光推送连接异常：{}", e)
            ServerResponse.createByFailure(ResponseCode.JPUSH_CONNECT_EXCEPTION)
        } catch (e: APIRequestException) {
            logger.info("创建新的请假，极光推送请求异常：{}", e)
            ServerResponse.createByFailure(
                ResponseCode.JPUSH_REQUEST_EXCEPTION,
                errorMsg = "${e.errorCode} ${e.errorMessage}"
            )
        } catch (e: Exception) {
            logger.info("{创建新的请假:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }

    /**
     * 删除请假
     */
    @ApiOperation(value = "删除请假")
    @PostMapping(path = ["/deleteLeave"])
    fun deleteLeave(
        @ApiParam(value = "请假id", required = true, example = "0") @RequestParam leaveId: Long
    ): ServerResponse<String> =
        try {
            ServerResponse.createBySuccess(leaveService.deleteLeave(leaveId))
        } catch (e: LeaveNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_LEAVE, errorMsg = e.message)
        } catch (e: Exception) {
            logger.info("{删除请假:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }

    /**
     * 获取请假信息
     */
    @ApiOperation(value = "获取请假信息")
    @PostMapping(path = ["/getLeave"])
    fun getLeave(
        @ApiParam(value = "请假id", required = true, example = "0") @RequestParam leaveId: Long
    ): ServerResponse<Leave> =
        try {
            ServerResponse.createBySuccess(leaveService.getLeave(leaveId))
        } catch (e: LeaveNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_LEAVE, errorMsg = e.message)
        } catch (e: Exception) {
            logger.info("{获取请假信息:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }

    /**
     * 教师审批请假
     */
    @ApiOperation(value = "教师审批请假")
    @PostMapping(path = ["/approvalTheLeave"])
    fun approvalTheLeave(
        @ApiParam(value = "请假id", required = true, example = "0") @RequestParam leaveId: Long,
        @ApiParam(
            value = "审批状态",
            required = true,
            example = "0"
        ) @RequestBody approvalStateAndDescription: ApprovalStateAndDescriptionDTO
    ): ServerResponse<Leave> =
        try {
            ServerResponse.createBySuccess(
                leaveService.approvalTheLeave(
                    leaveId,
                    approvalStateAndDescription.state,
                    approvalStateAndDescription.description
                )
            )
        } catch (e: LeaveNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_LEAVE, errorMsg = e.message)
        } catch (e: LeaveInfoInvalidException) {
            ServerResponse.createByFailure(ResponseCode.LEAVE_INFO_INVALID, errorMsg = e.message)
        } catch (e: APIConnectionException) {
            logger.info("教师审批请假，极光推送连接异常：{}", e)
            ServerResponse.createByFailure(ResponseCode.JPUSH_CONNECT_EXCEPTION)
        } catch (e: APIRequestException) {
            logger.info("教师审批请假，极光推送请求异常：{}", e)
            ServerResponse.createByFailure(
                ResponseCode.JPUSH_REQUEST_EXCEPTION,
                errorMsg = "${e.errorCode} ${e.errorMessage}"
            )
        } catch (e: Exception) {
            logger.info("{教师审批请假:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }

    /**
     * 获取该学生所有的请假信息
     */
    @ApiOperation(value = "获取该学生所有的请假信息")
    @PostMapping(path = ["/getLeaveFromStudent"])
    fun getLeaveFromStudent(
        @ApiParam(value = "学生id", required = true, example = "0") @RequestParam studentId: Long
    ): ServerResponse<List<Leave>> =
        try {
            ServerResponse.createBySuccess(leaveService.getLeaveFromStudent(studentId))
        } catch (e: StudentNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_USER)
        } catch (e: Exception) {
            logger.info("{获取请假信息:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }

    /**
     * 获取教师所有审批的请假
     */
    @ApiOperation(value = "获取教师所有审批的请假")
    @PostMapping(path = ["/getLeaveFromTeacher"])
    fun getLeaveFromTeacher(
        @ApiParam(value = "教师id", required = true, example = "0") @RequestParam teacherId: Long
    ): ServerResponse<List<Leave>> =
        try {
            ServerResponse.createBySuccess(leaveService.getLeaveFromTeacher(teacherId))
        } catch (e: TeacherNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_USER)
        } catch (e: Exception) {
            logger.info("{获取请假信息:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
}
