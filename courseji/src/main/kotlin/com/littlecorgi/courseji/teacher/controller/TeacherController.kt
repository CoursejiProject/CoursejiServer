package com.littlecorgi.courseji.teacher.controller

import com.littlecorgi.courseji.common.ResponseCode
import com.littlecorgi.courseji.common.ServerResponse
import com.littlecorgi.courseji.teacher.exception.PasswordErrorException
import com.littlecorgi.courseji.teacher.exception.PhoneAlreadyExistException
import com.littlecorgi.courseji.teacher.exception.TeacherAlreadyExistException
import com.littlecorgi.courseji.teacher.exception.TeacherInfoInvalidException
import com.littlecorgi.courseji.teacher.exception.TeacherNotFoundException
import com.littlecorgi.courseji.teacher.model.Teacher
import com.littlecorgi.courseji.teacher.service.TeacherService
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
@RequestMapping(path = ["/teacher"])
class TeacherController {

    @Autowired
    private lateinit var teacherService: TeacherService
    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * 添加新用户
     */
    @ApiOperation(value = "注册")
    @PostMapping(path = ["/signUp"])
    fun signUp(
        @ApiParam(value = "注册用户信息", required = true) @RequestBody user: Teacher
    ): ServerResponse<String> {
        return try {
            ServerResponse.createBySuccess(teacherService.signUp(user))
        } catch (e: TeacherAlreadyExistException) {
            ServerResponse.createByFailure(ResponseCode.USER_HAS_EXIST, errorMsg = e.message)
        } catch (e: PhoneAlreadyExistException) {
            ServerResponse.createByFailure(ResponseCode.USER_INFO_INVALID, errorMsg = "手机号已经注册过!")
        } catch (e: TeacherInfoInvalidException) {
            ServerResponse.createByFailure(ResponseCode.USER_INFO_INVALID, errorMsg = e.msg)
        } catch (e: Exception) {
            logger.info("{添加用户:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
    }

    @ApiOperation(value = "获取所有用户")
    @GetMapping(path = ["/getAllUser"])
    fun getAllUser(): ServerResponse<List<Teacher>> =
        try {
            ServerResponse.createBySuccess(teacherService.getAllUser())
        } catch (e: Exception) {
            logger.info("{获取所有用户:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }

    @ApiOperation(value = "根据email和password登录")
    @GetMapping(path = ["/signIn"])
    fun signIn(
        @ApiParam(value = "用户账号/邮箱", required = true) @RequestParam email: String,
        @ApiParam(value = "用户密码", required = true) @RequestBody password: String
    ): ServerResponse<Teacher> {
        return try {
            ServerResponse.createBySuccess(teacherService.signIn(email, password))
        } catch (e: TeacherNotFoundException) {
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
        @ApiParam(value = "旧密码", required = true) @RequestBody oldPassword: String,
        @ApiParam(value = "新密码", required = true) @RequestBody newPassword: String
    ): ServerResponse<String> {
        return try {
            ServerResponse.createBySuccess(
                teacherService.updatePassword(
                    email,
                    oldPassword,
                    newPassword
                )
            )
        } catch (e: TeacherNotFoundException) {
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
            ServerResponse.createBySuccess(teacherService.getCreatedDate(id))
        } catch (e: TeacherNotFoundException) {
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
            ServerResponse.createBySuccess(teacherService.getLastModifiedDate(id))
        } catch (e: TeacherNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_USER)
        } catch (e: Exception) {
            logger.info("{获取用户创建日期:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
    }
}
