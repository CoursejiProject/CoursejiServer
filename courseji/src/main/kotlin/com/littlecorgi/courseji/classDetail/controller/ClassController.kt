package com.littlecorgi.courseji.classDetail.controller

import com.littlecorgi.courseji.classDetail.model.Class
import com.littlecorgi.courseji.classDetail.service.ClassService
import com.littlecorgi.courseji.common.ResponseCode
import com.littlecorgi.courseji.common.ServerResponse
import com.littlecorgi.courseji.teacher.exception.TeacherNotFoundException
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @author littlecorgi
 * @date 2021/5/4
 */
@Api
@Slf4j
@RestController
@RequestMapping("/class")
class ClassController {

    @Autowired
    private lateinit var classService: ClassService
    private val logger = LoggerFactory.getLogger(javaClass)

    @ApiOperation(value = "创建班级")
    @GetMapping("/createClass")
    fun createClass(
        @ApiParam(value = "教师id", example = "0") @RequestParam teacherId: Long,
        @ApiParam(value = "班级名", example = "0") @RequestParam className: String
    ): ServerResponse<Long> =
        try {
            ServerResponse.createBySuccess(classService.createClass(teacherId, className))
        } catch (e: TeacherNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_USER, errorMsg = "没有此教师")
        } catch (e: Exception) {
            logger.info("加入班级: {}", e.message)
            ServerResponse.createByFailure(ResponseCode.FAILURE)
        }

    @ApiOperation(value = "获取该老师的所有班级")
    @GetMapping("/getAllClassFromTeacher")
    fun getAllClassFromTeacher(
        @ApiParam(
            value = "教师id",
            example = "0"
        ) @RequestParam teacherId: Long
    ): ServerResponse<List<Class>> =
        try {
            ServerResponse.createBySuccess(classService.getAllClassFromTeacher(teacherId))
        } catch (e: TeacherNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_USER, errorMsg = "没有此教师")
        } catch (e: Exception) {
            logger.info("获取该老师的所有班级: {}", e.message)
            ServerResponse.createByFailure(ResponseCode.FAILURE)
        }
}