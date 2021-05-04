package com.littlecorgi.courseji.course.service

import com.littlecorgi.courseji.course.exception.CourseAlreadyExistException
import com.littlecorgi.courseji.course.exception.CourseInfoInvalidException
import com.littlecorgi.courseji.course.exception.CourseNotFoundException
import com.littlecorgi.courseji.course.model.Course
import com.littlecorgi.courseji.teacher.exception.TeacherNotFoundException

/**
 * 课程信息的Service
 *
 * @author littlecorgi
 * @date 2021/4/18
 */
interface CourseService {

    /**
     * 添加新课程
     *
     * @param teacherId [com.littlecorgi.courseji.teacher.model.Teacher]主键ID
     * @param course 课程[com.littlecorgi.courseji.course.model.Course]对象
     * @return 添加成功
     * @throws TeacherNotFoundException 根据teacherId找不到数据时抛出此异常
     * @throws CourseAlreadyExistException 课程已经存在时抛出此异常
     * @throws CourseInfoInvalidException 课程信息不合法时抛出此异常
     */
    fun addNewCourse(teacherId: Long, course: Course): Long

    /**
     * 删除课程
     *
     * @param id 课程id [com.littlecorgi.courseji.course.module.Course#id]，Course表的主键
     * @return 删除成功
     * @throws CourseNotFoundException 根据id找不到课程时抛出此异常
     */
    fun deleteCourse(id: Long): String
}
