package com.littlecorgi.courseji.leave.controller

import com.littlecorgi.courseji.common.ResponseCode
import com.littlecorgi.courseji.common.ServerResponse
import com.littlecorgi.courseji.course.exception.CourseNotFoundException
import com.littlecorgi.courseji.leave.exception.LeaveAlreadyExistException
import com.littlecorgi.courseji.leave.exception.LeaveInfoInvalidException
import com.littlecorgi.courseji.leave.exception.LeaveNotFoundException
import com.littlecorgi.courseji.leave.model.Leave
import com.littlecorgi.courseji.leave.service.LeaveService
import com.littlecorgi.courseji.schedule.exception.ScheduleNotFoundException
import com.littlecorgi.courseji.student.exception.StudentNotFoundException
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
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
        @ApiParam(value = "学生id", required = true, example = "0") @RequestBody studentId: Long,
        @ApiParam(value = "课程id", required = true, example = "0") @RequestBody courseId: Long,
        @ApiParam(value = "请假信息", required = true, example = "0") @RequestBody leave: Leave
    ): ServerResponse<Leave> =
        try {
            ServerResponse.createBySuccess(leaveService.createLeave(studentId, courseId, leave))
        } catch (e: StudentNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_USER, errorMsg = e.message)
        } catch (e: CourseNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_COURSE, errorMsg = e.message)
        } catch (e: ScheduleNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_SCHEDULE, errorMsg = e.message)
        } catch (e: LeaveAlreadyExistException) {
            ServerResponse.createByFailure(ResponseCode.LEAVE_HAS_EXIST, errorMsg = e.message)
        } catch (e: LeaveInfoInvalidException) {
            ServerResponse.createByFailure(ResponseCode.LEAVE_INFO_INVALID, errorMsg = e.msg)
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
        leaveId: Long
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
        leaveId: Long
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
        leaveId: Long,
        approvalState: Int,
        approval: String
    ): ServerResponse<Leave> =
        try {
            ServerResponse.createBySuccess(leaveService.approvalTheLeave(leaveId, approvalState, approval))
        } catch (e: LeaveNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_LEAVE, errorMsg = e.message)
        } catch (e: LeaveInfoInvalidException) {
            ServerResponse.createByFailure(ResponseCode.LEAVE_INFO_INVALID, errorMsg = e.message)
        } catch (e: Exception) {
            logger.info("{教师审批请假:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
}