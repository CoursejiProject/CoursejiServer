package com.littlecorgi.courseji.course.controller

import com.littlecorgi.courseji.common.ResponseCode
import com.littlecorgi.courseji.common.ServerResponse
import com.littlecorgi.courseji.course.exception.CourseAlreadyExistException
import com.littlecorgi.courseji.course.exception.CourseInfoInvalidException
import com.littlecorgi.courseji.course.exception.CourseNotFoundException
import com.littlecorgi.courseji.course.model.Course
import com.littlecorgi.courseji.course.service.CourseService
import com.littlecorgi.courseji.teacher.exception.TeacherNotFoundException
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
 * 课程信息的Controller
 *
 * @author littlecorgi
 * @date 2021/4/18
 */
@Api
@Slf4j
@RestController
@RequestMapping(path = ["/course"])
class CourseController {

    @Autowired
    private lateinit var courseService: CourseService
    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * 添加新课程
     */
    @ApiOperation(value = "添加课程")
    @PostMapping(path = ["/addNew"])
    fun signUp(
        @ApiParam(value = "教师id", required = true, example = "1") @RequestParam teacherId: Long,
        @ApiParam(value = "添加的课程信息", required = true) @RequestBody course: Course
    ): ServerResponse<String> {
        return try {
            ServerResponse.createBySuccess(courseService.addNewCourse(teacherId, course))
        } catch (e: TeacherNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_USER, errorMsg = e.message)
        } catch (e: CourseAlreadyExistException) {
            ServerResponse.createByFailure(ResponseCode.COURSE_HAS_EXIST, errorMsg = e.message)
        } catch (e: CourseInfoInvalidException) {
            ServerResponse.createByFailure(ResponseCode.COURSE_INFO_INVALID, errorMsg = e.msg)
        } catch (e: Exception) {
            logger.info("{添加课程:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
    }

    /**
     * 删除课程
     */
    @ApiOperation(value = "删除课程")
    @GetMapping(path = ["/delete"])
    fun deleteCourse(
        @ApiParam(value = "要被删除的课程的id", required = true, example = "1") @RequestBody id: Long
    ): ServerResponse<String> {
        return try {
            ServerResponse.createBySuccess(courseService.deleteCourse(id))
        } catch (e: CourseNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_COURSE, errorMsg = e.message)
        } catch (e: Exception) {
            logger.info("{删除课程:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
    }
}
