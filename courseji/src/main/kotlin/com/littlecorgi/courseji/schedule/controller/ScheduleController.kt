package com.littlecorgi.courseji.schedule.controller

import com.littlecorgi.courseji.common.ResponseCode
import com.littlecorgi.courseji.common.ServerResponse
import com.littlecorgi.courseji.course.exception.CourseNotFoundException
import com.littlecorgi.courseji.course.model.Course
import com.littlecorgi.courseji.schedule.dto.JoinCourseDTO
import com.littlecorgi.courseji.schedule.exception.StudentAlreadyJoinedException
import com.littlecorgi.courseji.schedule.model.Schedule
import com.littlecorgi.courseji.schedule.service.ScheduleService
import com.littlecorgi.courseji.student.exception.StudentNotFoundException
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Schedule的Controller
 *
 * @author littlecorgi
 * @date 2021/4/19
 */
@Api
@Slf4j
@RestController
@RequestMapping(path = ["/schedule"])
class ScheduleController {

    @Autowired
    private lateinit var scheduleService: ScheduleService
    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * 加入课程
     */
    @ApiOperation(value = "加入课程")
    @GetMapping(path = ["/join"])
    fun joinCourse(
        @ApiParam(value = "加入课程的学生的id", required = true) @RequestBody joinCourseDTO: JoinCourseDTO
    ): ServerResponse<Schedule> {
        return try {
            ServerResponse.createBySuccess(scheduleService.joinCourse(joinCourseDTO.studentId, joinCourseDTO.courseId))
        } catch (e: StudentNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_USER, errorMsg = e.message)
        } catch (e: CourseNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_COURSE, errorMsg = e.message)
        } catch (e: StudentAlreadyJoinedException) {
            ServerResponse.createByFailure(ResponseCode.STUDENT_HAS_JOINED_COURSE, errorMsg = e.message)
        } catch (e: Exception) {
            logger.error("{加入课程:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
    }

    /**
     * 获取学生加入的所有课程
     */
    @ApiOperation(value = "获取学生加入的所有课程")
    @GetMapping(path = ["/getAllCourse"])
    fun getAllCourse(
        @ApiParam(value = "查询的学生的id", required = true, example = "0") @RequestParam studentId: Long
    ): ServerResponse<List<Course>> =
        try {
            ServerResponse.createBySuccess(scheduleService.getAllCourse(studentId))
        } catch (e: Exception) {
            logger.error("{获取所有课程:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
}
