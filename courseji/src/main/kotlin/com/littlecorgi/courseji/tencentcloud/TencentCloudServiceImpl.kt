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

    override fun compareFaceFromFile(studentId: Long, detectLiveFace: Boolean, picFile: MultipartFile): Float {
        val userFacePicUrl = fileService.uploadPicture(picFile)
        detectLiveFace(detectLiveFace, userFacePicUrl)
        val student = studentRepository.findById(studentId).orElseThrow { StudentNotFoundException() }

        return TencentCloudUtil.compareFace(userFacePicUrl, student.picture)
    }

    override fun compareFaceFromURL(studentId: Long, detectLiveFace: Boolean, picURL: String): Float {
        detectLiveFace(detectLiveFace, picURL)
        val student = studentRepository.findById(studentId).orElseThrow { StudentNotFoundException() }
        return TencentCloudUtil.compareFace(picURL, student.picture)
    }

    override fun compareMaskFaceFromFile(studentId: Long, detectLiveFace: Boolean, picFile: MultipartFile): Float {
        val userFacePicUrl = fileService.uploadPicture(picFile)
        detectLiveFace(detectLiveFace, userFacePicUrl)
        val student = studentRepository.findById(studentId).orElseThrow { StudentNotFoundException() }

        return TencentCloudUtil.compareMaskFace(userFacePicUrl, student.picture)
    }

    override fun compareMaskFaceFromURL(studentId: Long, detectLiveFace: Boolean, picURL: String): Float {
        detectLiveFace(detectLiveFace, picURL)
        val student = studentRepository.findById(studentId).orElseThrow { StudentNotFoundException() }
        return TencentCloudUtil.compareMaskFace(picURL, student.picture)
    }

    /*********************
     * 私有方法
     ********************/

    /**
     * 是否进行活体检测
     *
     * @param doDetect 是否进行活体检测
     * @param picURL 图片URL
     * @throws DetectLiveFaceException 如果活体检测得分小于40，则抛出异常
     */
    private fun detectLiveFace(doDetect: Boolean, picURL: String) {
        if (doDetect) {
            if (TencentCloudUtil.detectLiveFaceAccurate(picURL) < 40) {
                throw DetectLiveFaceException()
            }
        }
    }
}