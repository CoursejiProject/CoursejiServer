package com.littlecorgi.courseji.teacher.service.impl

import com.littlecorgi.courseji.common.constants.UserDataConstants
import com.littlecorgi.courseji.common.utils.isHttpOrHttps
import com.littlecorgi.courseji.teacher.model.Teacher
import com.littlecorgi.courseji.teacher.exception.PasswordErrorException
import com.littlecorgi.courseji.teacher.exception.UserAlreadyExistException
import com.littlecorgi.courseji.teacher.exception.UserInfoInvalidException
import com.littlecorgi.courseji.teacher.exception.UserNotFoundException
import com.littlecorgi.courseji.teacher.repository.TeacherRepository
import com.littlecorgi.courseji.teacher.service.TeacherService
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * UserService的实现类，具体处理业务，并抛出对应的异常
 *
 * @author littlecorgi
 * @date 2021/4/15
 */
@Slf4j
@Service
class TeacherServiceImpl : TeacherService {

    @Autowired
    private lateinit var teacherRepository: TeacherRepository
    private val logger = LoggerFactory.getLogger(javaClass)

    /**************************
     * 重写方法
     *************************/

    override fun signUp(user: Teacher): String {
        logger.info("添加新用户")
        if (teacherRepository.existsUserByEmail(user.email)) {
            throw UserAlreadyExistException()
        }
        user.apply {
            if (!avatar.isHttpOrHttps()) {
                avatar =
                    "https://user-gold-cdn.xitu.io/2018/6/20/1641b2b7bbbd3323?imageView2/1/w/180/h/180/q/85/format/webp/interlace/1"
            }
            if (!picture.isHttpOrHttps()) {
                throw UserInfoInvalidException("picture不是http/https链接")
            }
            stringLengthIsInvalid(name, info = "姓名", maxLength = UserDataConstants.NAME_MAX_LENGTH)
            stringLengthIsInvalid(
                email,
                info = "邮箱/账号",
                maxLength = UserDataConstants.EMAIL_MAX_LENGTH
            )
            stringLengthIsInvalid(
                password,
                info = "密码",
                UserDataConstants.PASSWORD_MIN_LENGTH,
                UserDataConstants.PASSWORD_MAX_LENGTH
            )
            stringLengthIsInvalid(
                phone,
                info = "手机号",
                UserDataConstants.PHONE_LENGTH,
                UserDataConstants.PHONE_LENGTH
            )
        }
        teacherRepository.save(user)
        return "新建用户成功."
    }

    override fun getAllUser(): Iterable<Teacher> {
        logger.info("获取所有用户")
        return teacherRepository.findAll()
    }

    override fun signIn(email: String, password: String): Teacher {
        logger.info("登录")
        val user = teacherRepository.findByEmail(email).orElse(null)
        if (user == null) {
            throw UserNotFoundException()
        } else {
            if (user.password != password) {
                throw PasswordErrorException()
            }
            return user
        }
    }

    override fun updatePassword(email: String, oldPassword: String, newPassword: String): String {
        logger.info("更新密码")
        val user = teacherRepository.findByEmail(email).orElse(null)
        if (user == null) {
            throw UserNotFoundException()
        } else {
            if (user.password != oldPassword) {
                throw PasswordErrorException()
            }
            user.password = newPassword
            teacherRepository.save(user)
            return "更新密码成功。"
        }
    }

    override fun getCreatedDate(id: Long): Long {
        val user = teacherRepository.findById(id).orElse(null)
        if (user == null) {
            throw UserNotFoundException()
        } else {
            return user.createdTime
        }
    }

    override fun getLastModifiedDate(id: Long): Long {
        val user = teacherRepository.findById(id).orElse(null)
        if (user == null) {
            throw UserNotFoundException()
        } else {
            return user.lastModifiedTime
        }
    }

    /**************************
     * 私有方法
     *************************/

    /**
     * 判断传入的字符串长度是否在某个区间内
     *
     * @param s 需要被判断的字符串
     * @param info 不合法时返回的异常信息中该字段的显示名，如name-姓名
     * @param minLength 最小长度，默认0
     * @param maxLength 最大长度，默认Int.MAX_VALUE
     */
    private fun stringLengthIsInvalid(
        s: String,
        info: String,
        minLength: Int = 0,
        maxLength: Int = Int.MAX_VALUE
    ): Boolean {
        return if (s.length in minLength..maxLength) {
            true
        } else {
            throw UserInfoInvalidException("${info}长度不符合，最短${minLength}，最长${maxLength}")
        }
    }
}
