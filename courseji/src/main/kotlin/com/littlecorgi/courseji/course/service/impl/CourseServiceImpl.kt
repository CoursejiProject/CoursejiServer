package com.littlecorgi.courseji.course.service.impl

import com.littlecorgi.courseji.course.exception.CourseAlreadyExistException
import com.littlecorgi.courseji.course.exception.CourseInfoInvalidException
import com.littlecorgi.courseji.course.exception.CourseNotFoundException
import com.littlecorgi.courseji.course.model.Course
import com.littlecorgi.courseji.course.repository.CourseRepository
import com.littlecorgi.courseji.course.service.CourseService
import com.littlecorgi.courseji.schedule.repository.ScheduleRepository
import com.littlecorgi.courseji.teacher.exception.TeacherNotFoundException
import com.littlecorgi.courseji.teacher.repository.TeacherRepository
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * 课程信息的Service实现类
 *
 * @author littlecorgi
 * @date 2021/4/18
 */
@Slf4j
@Service
class CourseServiceImpl : CourseService {

    @Autowired
    private lateinit var courseRepository: CourseRepository

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    @Autowired
    private lateinit var scheduleRepository: ScheduleRepository

    private val logger = LoggerFactory.getLogger(javaClass)

    /**************************
     * 重写方法
     *************************/

    override fun addNewCourse(teacherId: Long, course: Course): String {
        logger.info("添加新课程")
        val teacher = teacherRepository.findById(teacherId).orElseThrow { TeacherNotFoundException() }
        course.apply {
            if (courseRepository.existsCourseByRoomAndStartNodeAndEndNodeAndStartWeekAndEndWeekAndTypeAndDay(
                    room, startNode, endNode, startWeek, endWeek, type, day
                )
            ) {
                // 根据信息查询如果存在，则抛出异常
                throw CourseAlreadyExistException()
            }
            if (day !in 1..7) {
                throw CourseInfoInvalidException("周数不再0和8的区间内")
            }
            if (type !in 0..3) {
                throw CourseInfoInvalidException("单双周类型值必须是0/1/2")
            }
            if (endWeek < startWeek) {
                throw CourseInfoInvalidException("结束周小于开始周")
            }
            if (endNode < startNode) {
                throw CourseInfoInvalidException("结束节数小于开始节数")
            }
            this.teacher = teacher
        }
        courseRepository.save(course)
        return "保存成功。"
    }

    override fun deleteCourse(id: Long): String {
        logger.info("删除课程")
        val course = courseRepository.findById(id).orElseThrow { CourseNotFoundException() }
        // 先移除所有的schedule，再进行删除
        course.scheduleList?.forEach {
            scheduleRepository.deleteById(it.id!!)
        }
        courseRepository.deleteById(id)
        return "删除成功。"
    }
}
