package com.littlecorgi.courseji.student.service

import com.littlecorgi.courseji.student.exception.StudentAlreadyExistException
import com.littlecorgi.courseji.student.exception.StudentInfoInvalidException
import com.littlecorgi.courseji.student.exception.StudentNotFoundException
import com.littlecorgi.courseji.student.model.Student
import com.littlecorgi.courseji.teacher.exception.PasswordErrorException

/**
 * 用户相关Service
 *
 * @author littlecorgi
 * @date 2021/4/15
 */
interface StudentService {
    /**
     * 登录
     *
     * @return 登录后返回User对象
     * @throws StudentNotFoundException 根据id找不到数据时就抛出此异常
     * @throws PasswordErrorException 传入的oldPassword和数据库中保存的密码不一致时抛出此异常
     */
    fun signIn(email: String, password: String): Student

    /**
     * 注册
     *
     * @param user 注册需要的用户信息
     * @return 注册结果
     * @throws StudentAlreadyExistException 添加用户时发现用户已经存在抛出此异常
     * @throws StudentInfoInvalidException 用户信息不合法时抛出此异常
     */
    fun signUp(user: Student): String

    /**
     * 返回所有用户信息
     *
     * @return 所有用户 [kotlin.collections.List]
     */
    fun getAllUser(): List<Student>

    /**
     * 更新密码
     *
     * @param email 邮箱
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @throws StudentNotFoundException 根据id找不到数据时就抛出此异常
     * @throws PasswordErrorException 传入的oldPassword和数据库中保存的密码不一致时抛出此异常
     */
    fun updatePassword(email: String, oldPassword: String, newPassword: String): String

    /**
     * 获取用户创建日期
     *
     * @param id 用户id
     * @return 用户创建日期
     * @throws StudentNotFoundException 根据id找不到数据时就抛出此异常
     */
    fun getCreatedDate(id: Long): Long

    /**
     * 获取用户最后一次修改信息日期
     *
     * @param id 用户id
     * @return 用户最后一次修改信息日期
     * @throws StudentNotFoundException 根据id找不到数据时就抛出此异常
     */
    fun getLastModifiedDate(id: Long): Long

    /**
     * 根据StudentId获取对应数据
     *
     * @param studentId 学生id
     * @return 获取到的数据
     * @throws StudentNotFoundException 根据id找不到数据时就抛出此异常
     */
    fun findByStudentId(studentId: Long): Student
}
