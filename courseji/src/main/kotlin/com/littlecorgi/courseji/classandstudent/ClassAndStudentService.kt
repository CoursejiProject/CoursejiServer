package com.littlecorgi.courseji.classandstudent

import com.littlecorgi.courseji.classDetail.model.Class
import com.littlecorgi.courseji.student.model.Student

/**
 * 课程的Service
 */
interface ClassAndStudentService {
    /**
     * 加入班级
     *
     * @param studentId 学生id
     * @param classId 班级id
     * @throws [com.littlecorgi.courseji.student.exception.StudentNotFoundException] 找不到学生时抛出此异常
     * @throws ClassNotFoundException 找不到班级时抛出此异常
     */
    fun joinClass(studentId: Long, classId: Long): Long

    /**
     * 获取这个班级的所有学生
     *
     * @param classId 班级id
     * @throws ClassNotFoundException 找不到班级时抛出此异常
     */
    fun getAllStudentInTheClass(classId: Long): List<Student>

    /**
     * 获取这个学生加入的所有班级
     *
     * @param studentId 学生id
     * @throws [com.littlecorgi.courseji.student.exception.StudentNotFoundException] 找不到学生时抛出此异常
     */
    fun getAllClassFromTheStudent(studentId: Long): List<Class>
}
