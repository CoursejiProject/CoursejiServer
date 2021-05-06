package com.littlecorgi.courseji.checkon.controller

import com.littlecorgi.courseji.checkon.dto.StudentAndClassDTO
import com.littlecorgi.courseji.checkon.model.CheckOn
import com.littlecorgi.courseji.checkon.service.CheckOnService
import com.littlecorgi.courseji.common.ResponseCode
import com.littlecorgi.courseji.common.ServerResponse
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
 * 签到纪录的Controller
 *
 * @author littlecorgi
 * @date 2021/4/22
 */
@Api
@Slf4j
@RestController
@RequestMapping(path = ["/checkOn"])
class CheckOnController {

    @Autowired
    private lateinit var checkOnService: CheckOnService
    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * 参加签到
     *
     * @param studentId 学生id
     * @param attendanceId 考勤数据id
     * @param checkOn 签到数据
     */
    @ApiOperation(value = "参加签到")
    @PostMapping(path = ["/checkIn"])
    fun checkIn(
        @ApiParam(value = "学生id", required = true, example = "1") @RequestParam studentId: Long,
        @ApiParam(value = "考勤信息id", required = true, example = "1") @RequestParam attendanceId: Long,
        @ApiParam(value = "参与签到的数据", required = true) @RequestBody checkOn: CheckOn
    ): ServerResponse<CheckOn> =
        try {
            ServerResponse.createBySuccess(checkOnService.checkIn(studentId, attendanceId, checkOn))
        } catch (e: Exception) {
            logger.info("{参加签到:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }

    /**
     * 获取这名学生这个班级的签到纪录
     *
     * @param studentAndClassDTO 学生id和班级id数据类 [com.littlecorgi.courseji.checkon.dto.StudentAndClassDTO]
     */
    @ApiOperation(value = "获取这名学生这个班级的签到纪录")
    @PostMapping(path = ["/getTheStudentCheckInInfoForTheClass"])
    fun getTheStudentCheckInInfoForTheClass(
        @ApiParam(value = "学生id和班级id", required = true) @RequestBody studentAndClassDTO: StudentAndClassDTO
    ): ServerResponse<List<CheckOn>> =
        try {
            ServerResponse.createBySuccess(
                checkOnService.getTheStudentCheckInInfoForTheClass(
                    studentAndClassDTO.studentId,
                    studentAndClassDTO.classId
                )
            )
        } catch (e: Exception) {
            logger.info("{获取这名学生这个班级的签到纪录:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }

    @ApiOperation(value = "根据教师获取所有考勤记录")
    @PostMapping("/getAllCheckOnFromTeacher")
    fun getAllCheckOnFromTeacher(
        @ApiParam(value = "教师id", example = "0") @RequestParam teacherId: Long
    ): ServerResponse<List<CheckOn>> = try {
        ServerResponse.createBySuccess(checkOnService.getAllCheckOnFromTeacher(teacherId))
    } catch (e: TeacherNotFoundException) {
        ServerResponse.createByFailure(ResponseCode.NO_USER, errorMsg = "没有此教师")
    } catch (e: Exception) {
        logger.info("{获取这名学生这个班级的签到纪录:catch}", e)
        ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
    }

    /**
     * 获取这名学生所有课的签到纪录
     *
     * @param studentId 学生id
     */
    @ApiOperation(value = "获取这名学生所有课的签到纪录")
    @PostMapping(path = ["/getTheStudentAllCheckInInfo"])
    fun getTheStudentAllCheckInInfo(
        @ApiParam(value = "学生id", required = true, example = "1") @RequestParam studentId: Long
    ): ServerResponse<List<CheckOn>> =
        try {
            ServerResponse.createBySuccess(checkOnService.getTheStudentAllCheckInInfo(studentId))
        } catch (e: Exception) {
            logger.info("{获取这名学生所有课的签到纪录:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }

    /**
     * 获取签到时间
     *
     * @param checkOnId 签到纪录id
     */
    @ApiOperation(value = "获取签到时间")
    @PostMapping(path = ["/getCheckInTime"])
    fun getCheckInTime(
        @ApiParam(value = "签到id", required = true, example = "1") @RequestParam checkOnId: Long
    ): ServerResponse<Long> =
        try {
            ServerResponse.createBySuccess(checkOnService.getCheckInTime(checkOnId))
        } catch (e: Exception) {
            logger.info("{获取签到时间:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }

    /**
     * 获取签到地点
     *
     * @param checkOnId 签到纪录id
     */
    @ApiOperation(value = "获取签到地点")
    @PostMapping(path = ["/getCheckInLocation"])
    fun getCheckInLocation(
        @ApiParam(value = "签到id", required = true, example = "1") @RequestParam checkOnId: Long
    ): ServerResponse<Pair<Double, Double>> =
        try {
            ServerResponse.createBySuccess(checkOnService.getCheckInLocation(checkOnId))
        } catch (e: Exception) {
            logger.info("{获取签到地点:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
}
