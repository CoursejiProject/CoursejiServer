package com.littlecorgi.courseji.course.repository

import com.littlecorgi.courseji.course.model.Course
import org.springframework.data.repository.CrudRepository
import java.util.Optional

/**
 * 课程信息的Repository
 *
 * @author littlecorgi
 * @date 2021/4/18
 */
interface CourseRepository : CrudRepository<Course, Long> {

    /**
     * 根据教室、开始节数、总节数、开始周数、节数周数以及单双周来判断Course表中是否存在此数据
     *
     * @param room 教室
     * @param startNode 开始节数
     * @param step 总节数
     * @param startWeek 开始周数
     * @param endWeek 结束周数
     * @param type 单双周
     * @return 存在返回true，否则false
     */
    fun existsCourseByRoomAndStartNodeAndStepAndStartWeekAndEndWeekAndType(
        room: String,
        startNode: Int,
        step: Int,
        startWeek: Int,
        endWeek: Int,
        type: Int
    ): Boolean

    /**
     * 根据教室、开始节数、总节数、开始周数、节数周数以及单双周来获取Course表中对应数据
     *
     * @param room 教室
     * @param startNode 开始节数
     * @param step 总节数
     * @param startWeek 开始周数
     * @param endWeek 结束周数
     * @param type 单双周
     * @return 如果存在返回数据
     */
    fun findCourseByRoomAndStartNodeAndStepAndStartWeekAndEndWeekAndType(
        room: String,
        startNode: Int,
        step: Int,
        startWeek: Int,
        endWeek: Int,
        type: Int
    ): Optional<Course>
}
