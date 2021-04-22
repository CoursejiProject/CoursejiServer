package com.littlecorgi.courseji.attendance.controller

import com.littlecorgi.courseji.attendance.model.Attendance
import com.littlecorgi.courseji.attendance.service.AttendanceService
import com.littlecorgi.courseji.common.ResponseCode
import com.littlecorgi.courseji.common.ServerResponse
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @author littlecorgi
 * @date 2021/4/21
 */
@Slf4j
@RestController
@RequestMapping(path = ["/attendance"])
class AttendanceController {

    @Autowired
    private lateinit var attendanceService: AttendanceService

    /**
     * 新建签到
     */
    @ApiOperation(value = "创建签到")
    @PostMapping(path = ["/createAttendance"])
    fun createNewAttendance(
        @ApiParam(value = "发起签到教师id", required = true) @RequestParam teacherId: Long,
        @ApiParam(value = "签到的课程id", required = true) @RequestParam courseId: Long,
        @ApiParam(value = "签到信息", required = true) @RequestBody attendance: Attendance
    ): ServerResponse<Attendance> =
        try {
            ServerResponse.createBySuccess(attendanceService.createNewAttendance(teacherId, courseId, attendance))
        } catch (e: Exception) {
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }

    /**
     * 修改签到
     */
    @ApiOperation(value = "修改签到")
    @PostMapping(path = ["/updateAttendanceInfo"])
    fun updateAttendanceInfo(
        @ApiParam(value = "要被修改的签到id", required = true) @RequestParam attendanceId: Long,
        @ApiParam(value = "修改后的签到信息", required = true) @RequestBody attendance: Attendance
    ): ServerResponse<Attendance> =
        try {
            ServerResponse.createBySuccess(attendanceService.updateAttendanceInfo(attendanceId, attendance))
        } catch (e: Exception) {
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
}