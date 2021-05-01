package com.littlecorgi.courseji.tencentcloud

import com.littlecorgi.courseji.common.utils.TencentCloudUtil
import com.littlecorgi.courseji.file.FileService
import com.littlecorgi.courseji.student.exception.StudentNotFoundException
import com.littlecorgi.courseji.student.repository.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

/**
 *
 * @author littlecorgi
 * @date 2021/4/30
 */
@Service
class TencentCloudServiceImpl : TencentCloudService {

    @Autowired
    private lateinit var fileService: FileService

    @Autowired
    private lateinit var studentRepository: StudentRepository

    /*********************
     * 继承方法
     ********************/

    override fun compareFaceFromFile(studentId: Long, picFile: MultipartFile): String {
        val userFacePicUrl = fileService.uploadPicture(picFile)
        val student = studentRepository.findById(studentId).orElseThrow { StudentNotFoundException() }

        return TencentCloudUtil.compareFace(userFacePicUrl, student.picture)
    }

    override fun compareFaceFromURL(studentId: Long, picURL: String): String {
        val student = studentRepository.findById(studentId).orElseThrow { StudentNotFoundException() }
        return TencentCloudUtil.compareFace(picURL, student.picture)
    }

    override fun compareMaskFaceFromFile(studentId: Long, picFile: MultipartFile): String {
        val userFacePicUrl = fileService.uploadPicture(picFile)
        val student = studentRepository.findById(studentId).orElseThrow { StudentNotFoundException() }

        return TencentCloudUtil.compareMaskFace(userFacePicUrl, student.picture)
    }

    override fun compareMaskFaceFromURL(studentId: Long, picURL: String): String {
        val student = studentRepository.findById(studentId).orElseThrow { StudentNotFoundException() }
        return TencentCloudUtil.compareMaskFace(picURL, student.picture)
    }
}