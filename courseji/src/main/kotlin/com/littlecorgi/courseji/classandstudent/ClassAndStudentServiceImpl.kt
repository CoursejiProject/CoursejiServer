package com.littlecorgi.courseji.classandstudent

import com.littlecorgi.courseji.`class`.Class
import com.littlecorgi.courseji.`class`.ClassNotFoundException
import com.littlecorgi.courseji.`class`.ClassRepository
import com.littlecorgi.courseji.student.exception.StudentNotFoundException
import com.littlecorgi.courseji.student.model.Student
import com.littlecorgi.courseji.student.repository.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 *
 * @author littlecorgi
 * @date 2021/5/4
 */
@Service
class ClassAndStudentServiceImpl : ClassAndStudentService {

    @Autowired
    private lateinit var classAndStudentRepository: ClassAndStudentRepository

    @Autowired
    private lateinit var classRepository: ClassRepository

    @Autowired
    private lateinit var studentRepository: StudentRepository

    override fun joinClass(studentId: Long, classId: Long): Long {
        val student = studentRepository.findById(studentId).orElseThrow { StudentNotFoundException() }
        val theClass = classRepository.findById(classId).orElseThrow { ClassNotFoundException() }
        val classAndStudent = ClassAndStudent().apply {
            this.student = student
            this.classDetail = theClass
        }
        return classAndStudentRepository.save(classAndStudent).id!!
    }

    override fun getAllStudentInTheClass(classId: Long): List<Student> {
        val theClass = classRepository.findById(classId).orElseThrow { ClassNotFoundException() }
        return classAndStudentRepository.findAllByClassDetail(theClass).map {
            it.student
        }
    }

    override fun getAllClassFromTheStudent(studentId: Long): List<Class> {
        val student = studentRepository.findById(studentId).orElseThrow { StudentNotFoundException() }
        return classAndStudentRepository.findAllByStudent(student).map {
            it.classDetail
        }
    }
}