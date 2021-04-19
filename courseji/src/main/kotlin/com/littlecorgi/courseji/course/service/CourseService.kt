package com.littlecorgi.courseji.course.service

import com.littlecorgi.courseji.course.model.Course

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
     * @param course 课程[com.littlecorgi.courseji.course.model.Course]对象
     * @return 添加成功
     */
    fun addNewCourse(course: Course): String

    /**
     * 删除课程
     *
     * @param id 课程id [com.littlecorgi.courseji.course.module.Course#id]，Course表的主键
     * @return 删除成功
     */
    fun deleteCourse(id: Int): String
}
