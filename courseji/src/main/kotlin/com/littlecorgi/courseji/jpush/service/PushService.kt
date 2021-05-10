package com.littlecorgi.courseji.jpush.service

import cn.jpush.api.push.PushResult

/**
 *
 * @author littlecorgi
 * @date 2021/5/10
 */
interface PushService {

    /**
     * 给Android设备推送消息
     *
     * @param isTeacher 是否是教师，true为教师，false为学生
     * @param userId 用户id
     * @param alert 通知内容
     * @param title 通知标题
     * @throws [cn.jiguang.common.resp.APIConnectionException] 极光推送连接异常，可以稍后重试
     * @throws [cn.jiguang.common.resp.APIRequestException] 极光推送返回的异常信息，具体信息可以查看极光推送官方文档
     * @throws [com.littlecorgi.courseji.teacher.exception.TeacherNotFoundException] 当根据userId找不到教师用户时抛出此异常
     * @throws [com.littlecorgi.courseji.student.exception.StudentNotFoundException] 当根据userId找不到学生用户时抛出此异常
     */
    fun pushAndroidMessage(isTeacher: Boolean, userId: Long, alert: String, title: String): PushResult

    /**
     * 给Android设备发送自定义消息
     *
     * @param isTeacher 是否是教师，true为教师，false为学生
     * @param userId 用户id
     * @param msgContent 通知内容
     * @param title 通知标题
     * @throws [cn.jiguang.common.resp.APIConnectionException] 极光推送连接异常，可以稍后重试
     * @throws [cn.jiguang.common.resp.APIRequestException] 极光推送返回的异常信息，具体信息可以查看极光推送官方文档
     * @throws [com.littlecorgi.courseji.teacher.exception.TeacherNotFoundException] 当根据userId找不到教师用户时抛出此异常
     * @throws [com.littlecorgi.courseji.student.exception.StudentNotFoundException] 当根据userId找不到学生用户时抛出此异常
     */
    fun pushAndroidCustomMessage(isTeacher: Boolean, userId: Long, msgContent: String, title: String): PushResult
}