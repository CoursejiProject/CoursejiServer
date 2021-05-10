package com.littlecorgi.courseji.jpush.controller

import cn.jiguang.common.resp.APIConnectionException
import cn.jiguang.common.resp.APIRequestException
import cn.jpush.api.push.PushResult
import com.littlecorgi.courseji.common.ResponseCode
import com.littlecorgi.courseji.common.ServerResponse
import com.littlecorgi.courseji.jpush.service.PushService
import com.littlecorgi.courseji.student.exception.StudentNotFoundException
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
 * 极光推送-推送相关Controller
 *
 * @author littlecorgi
 * @date 2021/5/10
 */
@Api("极光推送服务")
@Slf4j
@RestController
@RequestMapping("/jpush/push")
class PushController {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    private lateinit var pushService: PushService

    @ApiOperation("发送Android平台推送消息")
    @GetMapping("/pushAndroidMessage")
    fun pushAndroidMessage(
        @ApiParam("true教师，false学生", example = "1") @RequestParam isTeacher: Boolean,
        @ApiParam("学生id", example = "1") @RequestParam userId: Long,
        @ApiParam("内容") @RequestParam alert: String,
        @ApiParam("标题") @RequestParam title: String
    ): ServerResponse<PushResult> =
        try {
            ServerResponse.createBySuccess(pushService.pushAndroidMessage(isTeacher, userId, alert, title))
        } catch (e: APIConnectionException) {
            logger.info("发送Android平台推送，极光推送连接异常：{}", e)
            ServerResponse.createByFailure(ResponseCode.JPUSH_CONNECT_EXCEPTION)
        } catch (e: APIRequestException) {
            logger.info("发送Android平台推送，极光推送请求异常：{}", e)
            ServerResponse.createByFailure(
                ResponseCode.JPUSH_REQUEST_EXCEPTION,
                errorMsg = "${e.errorCode} ${e.errorMessage}"
            )
        } catch (e: StudentNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_USER, errorMsg = "教师用户不存在")
        } catch (e: TeacherNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_USER, errorMsg = "教师用户不存在")
        } catch (e: Exception) {
            logger.info("发送Android平台推送：{}", e)
            ServerResponse.createByFailure(ResponseCode.FAILURE)
        }
}