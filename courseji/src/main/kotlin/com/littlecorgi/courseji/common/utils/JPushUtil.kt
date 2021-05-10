package com.littlecorgi.courseji.common.utils

import cn.jiguang.common.ClientConfig
import cn.jpush.api.JPushClient
import cn.jpush.api.push.PushResult
import cn.jpush.api.push.model.Message
import cn.jpush.api.push.model.Options
import cn.jpush.api.push.model.Platform
import cn.jpush.api.push.model.PushPayload
import cn.jpush.api.push.model.audience.Audience
import cn.jpush.api.push.model.notification.Notification
import com.google.gson.JsonObject

/**
 * 极光推送的工具类
 *
 * @author littlecorgi
 * @date 2021/5/11
 */
object JPushUtil {

    private val jpushStudentClient =
        JPushClient("aa378d843ff78eb6433a22b3", "84b20a7f0ba71c4a7961ef5c", null, ClientConfig.getInstance())

    private val jpushTeacherClient =
        JPushClient("a4ba22c5f71fdbbcb3ae0051", "d903ad14a1573503cff28e6b", null, ClientConfig.getInstance())

    private fun buildPushObject(alias: String, alert: String, title: String): PushPayload {
        return PushPayload.newBuilder()
            .setPlatform(Platform.android())
            .setAudience(Audience.alias(alias))
            .setNotification(Notification.android(alert, title, mapOf("alert_type" to "-1")))
            .setOptions(
                Options.newBuilder().setThirdPartyChannelV2(
                    mapOf("xiaomi" to JsonObject().apply { addProperty("distribution", "secondary_push") })
                ).build()
            )
            .build()
    }

    private fun buildCustomPushObject(alias: String, msgContent: String, title: String): PushPayload {
        val message = Message.Builder()
            .setTitle(title)
            .setMsgContent(msgContent)
            .build()
        return PushPayload.newBuilder()
            .setPlatform(Platform.android())
            .setAudience(Audience.alias(alias))
            .setMessage(message)
            .build()
    }

    fun sendStudentPush(alias: String, alert: String, title: String): PushResult =
        jpushStudentClient.sendPush(buildPushObject(alias, alert, title))

    fun sendTeacherPush(alias: String, alert: String, title: String): PushResult =
        jpushTeacherClient.sendPush(buildPushObject(alias, alert, title))

    fun sendStudentCustomMessage(alias: String, msgContent: String, title: String): PushResult =
        jpushStudentClient.sendPush(buildCustomPushObject(alias, msgContent, title))

    fun sendTeacherCustomMessage(alias: String, msgContent: String, title: String): PushResult =
        jpushTeacherClient.sendPush(buildCustomPushObject(alias, msgContent, title))
}