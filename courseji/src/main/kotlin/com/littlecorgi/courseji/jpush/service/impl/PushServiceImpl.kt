package com.littlecorgi.courseji.jpush.service.impl

import cn.jpush.api.push.PushResult
import com.littlecorgi.courseji.common.utils.JPushUtil.sendStudentCustomMessage
import com.littlecorgi.courseji.common.utils.JPushUtil.sendStudentPush
import com.littlecorgi.courseji.common.utils.JPushUtil.sendTeacherCustomMessage
import com.littlecorgi.courseji.common.utils.JPushUtil.sendTeacherPush
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

    override fun pushAndroidMessage(isTeacher: Boolean, userId: Long, alert: String, title: String): PushResult {
        val alias = if (isTeacher) {
            val teacher = teacherRepository.findById(userId).orElseThrow { TeacherNotFoundException() }
            "教师$userId"
        } else {
            val student = studentRepository.findById(userId).orElseThrow { StudentNotFoundException() }
            "学生$userId"
        }
        return if (isTeacher) sendTeacherPush(alias, alert, title) else sendStudentPush(alias, alert, title)
    }

    override fun pushAndroidCustomMessage(
        isTeacher: Boolean,
        userId: Long,
        msgContent: String,
        title: String
    ): PushResult {
        val alias = if (isTeacher) {
            val teacher = teacherRepository.findById(userId).orElseThrow { TeacherNotFoundException() }
            "教师$userId"
        } else {
            val student = studentRepository.findById(userId).orElseThrow { StudentNotFoundException() }
            "学生$userId"
        }
        return if (isTeacher) {
            sendTeacherCustomMessage(alias, msgContent, title)
        } else {
            sendStudentCustomMessage(alias, msgContent, title)
        }
    }
}