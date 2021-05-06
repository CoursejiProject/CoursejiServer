package com.littlecorgi.courseji.attendance.controller

import com.littlecorgi.courseji.attendance.exception.AttendanceInfoInvalidException
import com.littlecorgi.courseji.attendance.model.Attendance
import com.littlecorgi.courseji.attendance.service.AttendanceService
import com.littlecorgi.courseji.common.ResponseCode
import com.littlecorgi.courseji.common.ServerResponse
import com.littlecorgi.courseji.course.exception.CourseNotFoundException
import com.littlecorgi.courseji.teacher.exception.TeacherNotFoundException
import io.swagger.annotations.Api
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
@Api
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
        @ApiParam(value = "签到的班级id", required = true, example = "1") @RequestParam classId: Long,
        @ApiParam(value = "签到信息", required = true) @RequestBody attendance: Attendance
    ): ServerResponse<Attendance> =
        try {
            ServerResponse.createBySuccess(attendanceService.createNewAttendance(classId, attendance))
        } catch (e: CourseNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_COURSE, errorMsg = e.message)
        } catch (e: AttendanceInfoInvalidException) {
            ServerResponse.createByFailure(ResponseCode.ATTENDANCE_INFO_INVALID, errorMsg = e.msg)
        } catch (e: Exception) {
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }

    /**
     * 修改签到
     */
    @ApiOperation(value = "修改签到")
    @PostMapping(path = ["/updateAttendanceInfo"])
    fun updateAttendanceInfo(
        @ApiParam(value = "要被修改的签到id", required = true, example = "1") @RequestParam attendanceId: Long,
        @ApiParam(value = "修改后的签到信息", required = true) @RequestBody attendance: Attendance
    ): ServerResponse<Attendance> =
        try {
            ServerResponse.createBySuccess(attendanceService.updateAttendanceInfo(attendanceId, attendance))
        } catch (e: AttendanceInfoInvalidException) {
            ServerResponse.createByFailure(ResponseCode.ATTENDANCE_INFO_INVALID, errorMsg = e.msg)
        } catch (e: Exception) {
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }

    /**
     * 修改签到
     */
    @ApiOperation(value = "根据教师获取所有签到纪录")
    @PostMapping(path = ["/getAllAttendanceFromTeacher"])
    fun getAllAttendanceFromTeacher(
        @ApiParam(value = "签到的班级id", required = true, example = "1") @RequestParam teacherId: Long
    ): ServerResponse<List<Attendance>> =
        try {
            ServerResponse.createBySuccess(attendanceService.getAllAttendanceFromTeacher(teacherId))
        } catch (e: TeacherNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_USER)
        } catch (e: Exception) {
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
}
