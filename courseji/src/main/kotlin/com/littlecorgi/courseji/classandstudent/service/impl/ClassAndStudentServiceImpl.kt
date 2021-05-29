package com.littlecorgi.courseji.classandstudent.service.impl

import com.littlecorgi.courseji.classDetail.exception.ClassNotFoundException
import com.littlecorgi.courseji.classDetail.model.Class
import com.littlecorgi.courseji.classDetail.repository.ClassRepository
import com.littlecorgi.courseji.classandstudent.model.ClassAndStudent
import com.littlecorgi.courseji.classandstudent.repository.ClassAndStudentRepository
import com.littlecorgi.courseji.classandstudent.service.ClassAndStudentService
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
        val savedClassAndStudent = classAndStudentRepository.save(classAndStudent)
        theClass.studentNum++
        classRepository.save(theClass)
        // 因为能走到这步数据肯定保存了，所以直接强制不为null返回出去
        return savedClassAndStudent.id!!
    }

    override fun getAllStudentInTheClass(classId: Long): List<Student> {
        val theClass = classRepository.findById(classId).orElseThrow { ClassNotFoundException() }
        return classAndStudentRepository.findAllByClassDetail(theClass).map {
            it.student
        }.sortedByDescending { it.name }
    }

    override fun getAllClassFromTheStudent(studentId: Long): List<Class> {
        val student = studentRepository.findById(studentId).orElseThrow { StudentNotFoundException() }
        return classAndStudentRepository.findAllByStudent(student).map {
            it.classDetail
        }
    }
}