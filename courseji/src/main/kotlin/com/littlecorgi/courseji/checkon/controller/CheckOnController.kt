package com.littlecorgi.courseji.checkon.controller

import com.littlecorgi.courseji.checkon.model.CheckOn
import com.littlecorgi.courseji.checkon.service.CheckOnService
import com.littlecorgi.courseji.common.ResponseCode
import com.littlecorgi.courseji.common.ServerResponse
import io.swagger.annotations.Api
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
     * @param scheduleId 考勤数据id
     * @param checkOn 签到数据
     */
    @PostMapping(path = ["/checkIn"])
    fun checkIn(
        @RequestParam studentId: Long,
        @RequestParam scheduleId: Long,
        @RequestBody checkOn: CheckOn
    ): ServerResponse<CheckOn> =
        try {
            ServerResponse.createBySuccess(checkOnService.checkIn(studentId, scheduleId, checkOn))
        } catch (e: Exception) {
            logger.info("{参加签到:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }

    /**
     * 获取这名学生这门课的签到纪录
     *
     * @param studentId 学生id
     * @param courseId 课程id
     */
    @GetMapping(path = ["/getTheStudentCheckInInfoForTheClass"])
    fun getTheStudentCheckInInfoForTheClass(
        @RequestParam studentId: Long,
        @RequestParam courseId: Long
    ): ServerResponse<List<CheckOn>> =
        try {
            ServerResponse.createBySuccess(checkOnService.getTheStudentCheckInInfoForTheClass(studentId, courseId))
        } catch (e: Exception) {
            logger.info("{获取这名学生这门课的签到纪录:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }

    /**
     * 获取这名学生所有课的签到纪录
     *
     * @param studentId 学生id
     */
    @GetMapping(path = ["/getTheStudentAllCheckInInfo"])
    fun getTheStudentAllCheckInInfo(
        @RequestParam studentId: Long
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
    @GetMapping(path = ["/getCheckInTime"])
    fun getCheckInTime(
        @RequestParam checkOnId: Long
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
    @GetMapping(path = ["/getCheckInLocation"])
    fun getCheckInLocation(
        @RequestParam checkOnId: Long
    ): ServerResponse<Pair<Float, Float>> =
        try {
            ServerResponse.createBySuccess(checkOnService.getCheckInLocation(checkOnId))
        } catch (e: Exception) {
            logger.info("{获取签到地点:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
}