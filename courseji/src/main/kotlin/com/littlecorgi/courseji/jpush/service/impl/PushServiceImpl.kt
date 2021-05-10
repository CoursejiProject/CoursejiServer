package com.littlecorgi.courseji.jpush.service.impl

import cn.jiguang.common.ClientConfig
import cn.jpush.api.JPushClient
import cn.jpush.api.push.PushResult
import cn.jpush.api.push.model.Platform
import cn.jpush.api.push.model.PushPayload
import cn.jpush.api.push.model.audience.Audience
import cn.jpush.api.push.model.notification.Notification
import com.littlecorgi.courseji.jpush.service.PushService
import com.littlecorgi.courseji.student.exception.StudentNotFoundException
import com.littlecorgi.courseji.student.repository.StudentRepository
import com.littlecorgi.courseji.teacher.exception.TeacherNotFoundException
import com.littlecorgi.courseji.teacher.repository.TeacherRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 *
 * @author littlecorgi
 * @date 2021/5/10
 */
@Service
class PushServiceImpl : PushService {

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    @Autowired
    private lateinit var studentRepository: StudentRepository

    private val jpushStudentClient =
        JPushClient("aa378d843ff78eb6433a22b3", "84b20a7f0ba71c4a7961ef5c", null, ClientConfig.getInstance())

    private val jpushTeacherClient =
        JPushClient("a4ba22c5f71fdbbcb3ae0051", "d903ad14a1573503cff28e6b", null, ClientConfig.getInstance())

    override fun pushAndroidMessage(isTeacher: Boolean, userId: Long, alert: String, title: String): PushResult {
        val alias = if (isTeacher) {
            val teacher = teacherRepository.findById(userId).orElseThrow { TeacherNotFoundException() }
            "教师$userId"
        } else {
            val student = studentRepository.findById(userId).orElseThrow { StudentNotFoundException() }
            "学生$userId"
        }
        val payload = buildPushObject(alias, alert, title)
        return if (isTeacher) jpushTeacherClient.sendPush(payload) else jpushStudentClient.sendPush(payload)
    }

    companion object {
        private fun buildPushObject(alias: String, alert: String, title: String): PushPayload =
            PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.android(alert, title, null))
                .build()
    }
}