package com.littlecorgi.courseji.`class`

import com.littlecorgi.courseji.teacher.exception.TeacherNotFoundException
import com.littlecorgi.courseji.teacher.repository.TeacherRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 *
 * @author littlecorgi
 * @date 2021/5/4
 */
@Service
class ClassServiceImpl : ClassService {

    @Autowired
    private lateinit var classRepository: ClassRepository

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    override fun createClass(teacherId: Long, className: String): Long {
        val teacher = teacherRepository.findById(teacherId).orElseThrow { TeacherNotFoundException() }
        val newClass = Class().apply {
            this.name = className
            this.teacher = teacher
        }
        return (classRepository.save(newClass)).id!!
    }
}