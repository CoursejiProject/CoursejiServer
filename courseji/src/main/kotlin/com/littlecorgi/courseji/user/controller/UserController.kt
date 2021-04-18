package com.littlecorgi.courseji.user.controller

import com.littlecorgi.courseji.common.ResponseCode
import com.littlecorgi.courseji.common.ServerResponse
import com.littlecorgi.courseji.user.exception.PasswordErrorException
import com.littlecorgi.courseji.user.exception.UserAlreadyExistException
import com.littlecorgi.courseji.user.exception.UserInfoInvalidException
import com.littlecorgi.courseji.user.exception.UserNotFoundException
import com.littlecorgi.courseji.user.model.User
import com.littlecorgi.courseji.user.service.UserService
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
import java.sql.Date

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
@RequestMapping(path = ["/user"])
class UserController {

    @Autowired
    private lateinit var userService: UserService
    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * 添加新用户
     */
    @ApiOperation(value = "注册")
    @PostMapping(path = ["/signUp"])
    fun signUp(
        @ApiParam(value = "注册用户信息", required = true) @RequestBody user: User
    ): ServerResponse<String> {
        return try {
            ServerResponse.createBySuccess(userService.signUp(user))
        } catch (e: UserAlreadyExistException) {
            ServerResponse.createByFailure(ResponseCode.USER_HAS_EXIST, errorMsg = e.message)
        } catch (e: UserInfoInvalidException) {
            ServerResponse.createByFailure(ResponseCode.USER_INFO_INVALID, errorMsg = e.msg)
        } catch (e: Exception) {
            logger.info("{添加用户:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
    }

    @ApiOperation(value = "获取所有用户")
    @GetMapping(path = ["/getAllUser"])
    fun getAllUser(): ServerResponse<Iterable<User>> =
        try {
            ServerResponse.createBySuccess(userService.getAllUser())
        } catch (e: Exception) {
            logger.info("{获取所有用户:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }


    @ApiOperation(value = "根据email和password登录")
    @GetMapping(path = ["/signIn"])
    fun signIn(
        @ApiParam(value = "用户账号/邮箱", required = true) @RequestParam email: String,
        @ApiParam(value = "用户密码", required = true) @RequestParam password: String
    ): ServerResponse<User> {
        return try {
            ServerResponse.createBySuccess(userService.signIn(email, password))
        } catch (e: UserNotFoundException) {
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
        @ApiParam(value = "旧密码", required = true) @RequestParam oldPassword: String,
        @ApiParam(value = "新密码", required = true) @RequestParam newPassword: String
    ): ServerResponse<String> {
        return try {
            ServerResponse.createBySuccess(
                userService.updatePassword(
                    email,
                    oldPassword,
                    newPassword
                )
            )
        } catch (e: UserNotFoundException) {
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
        @ApiParam(value = "需要查询的用户的id", required = true) @RequestParam id: Int
    ): ServerResponse<Date> {
        return try {
            ServerResponse.createBySuccess(userService.getCreatedDate(id))
        } catch (e: UserNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_USER)
        } catch (e: Exception) {
            logger.info("{获取用户创建日期:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
    }

    @ApiOperation(value = "获取用户最后一次信息修改日期")
    @GetMapping(path = ["/getLastModifiedDate"])
    fun getLastModifiedDate(
        @ApiParam(value = "需要查询的用户的id", required = true) @RequestParam id: Int
    ): ServerResponse<Date> {
        return try {
            ServerResponse.createBySuccess(userService.getLastModifiedDate(id))
        } catch (e: UserNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_USER)
        } catch (e: Exception) {
            logger.info("{获取用户创建日期:catch}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
    }
}