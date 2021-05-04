package com.littlecorgi.courseji.classandstudent

import com.littlecorgi.courseji.`class`.Class
import com.littlecorgi.courseji.common.ResponseCode
import com.littlecorgi.courseji.common.ServerResponse
import com.littlecorgi.courseji.student.exception.StudentNotFoundException
import com.littlecorgi.courseji.student.model.Student
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
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
@RequestMapping("/classAndStudent")
class ClassAndStudentController {

    @Autowired
    private lateinit var classAndStudentService: ClassAndStudentService
    private val logger = LoggerFactory.getLogger(javaClass)

    @ApiOperation(value = "加入班级")
    @PostMapping("/joinClass")
    fun joinClass(
        @ApiParam(value = "学生id", example = "0") @RequestParam studentId: Long,
        @ApiParam(value = "班级id", example = "0") @RequestParam classId: Long
    ): ServerResponse<Long> =
        try {
            ServerResponse.createBySuccess(classAndStudentService.joinClass(studentId, classId))
        } catch (e: StudentNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_USER, errorMsg = "学生不存在")
        } catch (e: ClassNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_CLASS)
        } catch (e: Exception) {
            logger.info("加入班级: {}", e.message)
            ServerResponse.createByFailure(ResponseCode.FAILURE)
        }

    @ApiOperation("获取这个班级的所有学生")
    @GetMapping("/getAllStudentInTheClass")
    fun getAllStudentInTheClass(
        @ApiParam(value = "班级id", example = "0") @RequestParam classId: Long
    ): ServerResponse<List<Student>> = try {
        ServerResponse.createBySuccess(classAndStudentService.getAllStudentInTheClass(classId))
    } catch (e: ClassNotFoundException) {
        ServerResponse.createByFailure(ResponseCode.NO_CLASS)
    } catch (e: Exception) {
        logger.info("获取这个班级的所有学生:{}", e.message)
        ServerResponse.createByFailure(ResponseCode.FAILURE)
    }

    @ApiOperation("获取这个学生加入的所有班级")
    @GetMapping("/getAllClassFromTheStudent")
    fun getAllClassFromTheStudent(
        @ApiParam(value = "学生id", example = "0") @RequestParam studentId: Long
    ): ServerResponse<List<Class>> = try {
        ServerResponse.createBySuccess(classAndStudentService.getAllClassFromTheStudent(studentId))
    } catch (e: StudentNotFoundException) {
        ServerResponse.createByFailure(ResponseCode.NO_USER, errorMsg = "没有此学生")
    } catch (e: Exception) {
        logger.info("获取这个班级的所有学生:{}", e.message)
        ServerResponse.createByFailure(ResponseCode.FAILURE)
    }
}