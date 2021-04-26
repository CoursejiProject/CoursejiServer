package com.littlecorgi.courseji.checkon.controller

import com.littlecorgi.courseji.checkon.dto.StudentAndCourseDTO
import com.littlecorgi.courseji.checkon.model.CheckOn
import com.littlecorgi.courseji.checkon.service.CheckOnService
import com.littlecorgi.courseji.common.ResponseCode
import com.littlecorgi.courseji.common.ServerResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
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
     * 获取这名学生这门课的签到纪录
     *
     * @param studentAndCourseDTO 学生id和课程id数据类 [com.littlecorgi.courseji.checkon.dto.StudentAndCourseDTO]
     */
    @ApiOperation(value = "获取这名学生这门课的签到纪录")
    @GetMapping(path = ["/getTheStudentCheckInInfoForTheClass"])
    fun getTheStudentCheckInInfoForTheClass(
        @ApiParam(value = "学生id和课程id", required = true) @RequestBody studentAndCourseDTO: StudentAndCourseDTO
    ): ServerResponse<List<CheckOn>> =
        try {
            ServerResponse.createBySuccess(
                checkOnService.getTheStudentCheckInInfoForTheClass(
                    studentAndCourseDTO.studentId,
                    studentAndCourseDTO.courseId
                )
            )
        } catch (e: Exception) {
            logger.info("{获取这名学生这门课的签到纪录:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }

    /**
     * 获取这名学生所有课的签到纪录
     *
     * @param studentId 学生id
     */
    @ApiOperation(value = "获取这名学生所有课的签到纪录")
    @GetMapping(path = ["/getTheStudentAllCheckInInfo"])
    fun getTheStudentAllCheckInInfo(
        @ApiParam(value = "学生id", required = true) @RequestBody studentId: Long
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
    @GetMapping(path = ["/getCheckInTime"])
    fun getCheckInTime(
        @ApiParam(value = "签到id", required = true) @RequestBody checkOnId: Long
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
    @GetMapping(path = ["/getCheckInLocation"])
    fun getCheckInLocation(
        @ApiParam(value = "签到id", required = true) @RequestBody checkOnId: Long
    ): ServerResponse<Pair<Float, Float>> =
        try {
            ServerResponse.createBySuccess(checkOnService.getCheckInLocation(checkOnId))
        } catch (e: Exception) {
            logger.info("{获取签到地点:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
}
