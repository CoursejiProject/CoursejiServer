package com.littlecorgi.courseji.course.service.impl

import com.littlecorgi.courseji.course.exception.CourseAlreadyExistException
import com.littlecorgi.courseji.course.exception.CourseInfoInvalidException
import com.littlecorgi.courseji.course.exception.CourseNotFoundException
import com.littlecorgi.courseji.course.model.Course
import com.littlecorgi.courseji.course.repository.CourseRepository
import com.littlecorgi.courseji.course.service.CourseService
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
    private val logger = LoggerFactory.getLogger(javaClass)

    /**************************
     * 重写方法
     *************************/

    override fun addNewCourse(course: Course): String {
        logger.info("添加新课程")
        course.apply {
            if (courseRepository.existsCourseByRoomAndStartNodeAndStepAndStartWeekAndEndWeekAndType(
                    room, startNode, step, startWeek, endWeek, type
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
        }
        courseRepository.save(course)
        return "保存成功。"
    }

    override fun deleteCourse(id: Int): String {
        logger.info("删除课程")
        if (!courseRepository.existsById(id)) {
            throw CourseNotFoundException()
        }
        courseRepository.deleteById(id)
        return "删除成功。"
    }
}