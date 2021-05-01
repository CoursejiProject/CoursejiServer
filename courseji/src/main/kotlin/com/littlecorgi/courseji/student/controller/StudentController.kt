package com.littlecorgi.courseji.student.controller

import com.littlecorgi.courseji.common.ResponseCode
import com.littlecorgi.courseji.common.ServerResponse
import com.littlecorgi.courseji.student.dto.UpdatePasswordDTO
import com.littlecorgi.courseji.student.exception.PasswordErrorException
import com.littlecorgi.courseji.student.exception.PhoneAlreadyExistException
import com.littlecorgi.courseji.student.exception.StudentAlreadyExistException
import com.littlecorgi.courseji.student.exception.StudentInfoInvalidException
import com.littlecorgi.courseji.student.exception.StudentNotFoundException
import com.littlecorgi.courseji.student.model.Student
import com.littlecorgi.courseji.student.service.StudentService
import com.tencentcloudapi.common.exception.TencentCloudSDKException
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
 * User相关的Controller
 * 在这一层不处理具体的业务逻辑，只处理UserService出现的异常并包装返回
 *
 * @author littlecorgi
 * @date 2021/4/13
 */
@Api
@Slf4j
@RestController
@RequestMapping(path = ["/student"])
class StudentController {

    @Autowired
    private lateinit var studentService: StudentService
    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * 添加新用户
     */
    @ApiOperation(value = "注册")
    @PostMapping(path = ["/signUp"])
    fun signUp(
        @ApiParam(value = "注册用户信息", required = true) @RequestBody student: Student
    ): ServerResponse<String> {
        return try {
            ServerResponse.createBySuccess(studentService.signUp(student))
        } catch (e: StudentAlreadyExistException) {
            ServerResponse.createByFailure(ResponseCode.USER_HAS_EXIST, errorMsg = e.message)
        } catch (e: StudentInfoInvalidException) {
            ServerResponse.createByFailure(ResponseCode.USER_INFO_INVALID, errorMsg = e.msg)
        } catch (e: PhoneAlreadyExistException) {
            ServerResponse.createByFailure(ResponseCode.USER_INFO_INVALID, errorMsg = "手机号已经注册过!")
        } catch (e: Exception) {
            logger.info("{添加用户:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
    }

    @ApiOperation(value = "获取所有用户")
    @GetMapping(path = ["/getAllUser"])
    fun getAllUser(): ServerResponse<List<Student>> =
        try {
            ServerResponse.createBySuccess(studentService.getAllUser())
        } catch (e: Exception) {
            logger.info("{获取所有用户:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }

    @ApiOperation(value = "根据email和password登录")
    @GetMapping(path = ["/signIn"])
    fun signIn(
        @ApiParam(value = "用户账号/邮箱", required = true) @RequestParam email: String,
        @ApiParam(value = "用户密码", required = true) @RequestBody password: String
    ): ServerResponse<Student> {
        return try {
            ServerResponse.createBySuccess(studentService.signIn(email, password))
        } catch (e: StudentNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_USER)
        } catch (e: PasswordErrorException) {
            ServerResponse.createByFailure(ResponseCode.PASSWORD_ERROR)
        } catch (e: Exception) {
            logger.info("{登录:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
    }

    @ApiOperation(value = "更新密码")
    @GetMapping(path = ["/updatePassword"])
    fun updatePassword(
        @ApiParam(value = "用户名/邮箱", required = true) @RequestParam email: String,
        @ApiParam(value = "密码包装类，里面包含旧密码和新密码", required = true) @RequestBody passwordDTO: UpdatePasswordDTO
    ): ServerResponse<String> {
        return try {
            ServerResponse.createBySuccess(
                studentService.updatePassword(
                    email,
                    passwordDTO.oldPassword,
                    passwordDTO.newPassword
                )
            )
        } catch (e: StudentNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_USER)
        } catch (e: PasswordErrorException) {
            ServerResponse.createByFailure(ResponseCode.PASSWORD_ERROR)
        } catch (e: Exception) {
            logger.info("{更新密码:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
    }

    @ApiOperation(value = "获取用户创建日期")
    @GetMapping(path = ["/getCreatedDate"])
    fun getCreatedDate(
        @ApiParam(value = "需要查询的用户的id", required = true, example = "1") @RequestParam id: Long
    ): ServerResponse<Long> {
        return try {
            ServerResponse.createBySuccess(studentService.getCreatedDate(id))
        } catch (e: StudentNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_USER)
        } catch (e: Exception) {
            logger.info("{获取用户创建日期:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
    }

    @ApiOperation(value = "获取用户最后一次信息修改日期")
    @GetMapping(path = ["/getLastModifiedDate"])
    fun getLastModifiedDate(
        @ApiParam(value = "需要查询的用户的id", required = true, example = "1") @RequestParam id: Long
    ): ServerResponse<Long> {
        return try {
            ServerResponse.createBySuccess(studentService.getLastModifiedDate(id))
        } catch (e: StudentNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_USER)
        } catch (e: Exception) {
            logger.info("{获取用户创建日期:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
    }

    @ApiOperation(value = "删除用户")
    @GetMapping(path = ["/deleteStudent"])
    fun deleteStudent(
        @ApiParam(value = "需要删除的用户的id", required = true, example = "1") @RequestParam id: Long,
        @ApiParam(value = "token密码", required = true) @RequestBody password: String
    ): ServerResponse<String> {
        return try {
            if (password == "67673") {
                ServerResponse.createBySuccess(studentService.deleteStudent(id))
            } else {
                ServerResponse.createByFailure(ResponseCode.PASSWORD_ERROR, errorMsg = "密码错误")
            }
        } catch (e: StudentNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_USER, errorMsg = e.message)
        } catch (e: TencentCloudSDKException) {
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        } catch (e: Exception) {
            logger.info("{删除用户:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
    }
}
