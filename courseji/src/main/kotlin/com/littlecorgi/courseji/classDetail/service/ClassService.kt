package com.littlecorgi.courseji.classDetail.service

import com.littlecorgi.courseji.classDetail.model.Class

/**
 * 课程的Service
 */
interface ClassService {
    /**
     * 加入班级
     *
     * @param teacherId 教师id
     * @param className 班级名
     * @throws [com.littlecorgi.courseji.teacher.exception.TeacherNotFoundException] 找不到教师时抛出此异常
     */
    fun createClass(teacherId: Long, className: String): Long

    /**
     * 获取该老师的所有班级
     *
     * @param teacherId 教师id
     * @throws [com.littlecorgi.courseji.teacher.exception.TeacherNotFoundException] 找不到教师时抛出此异常
     */
    fun getAllClassFromTeacher(teacherId: Long): List<Class>
}
