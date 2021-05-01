package com.littlecorgi.courseji.tencentcloud

import org.springframework.web.multipart.MultipartFile

/**
 *
 * @author littlecorgi
 * @date 2021/4/30
 */
interface TencentCloudService {

    /**
     * 腾讯云/人脸对比，传输文件
     *
     * @param studentId 学生id [com.littlecorgi.courseji.student.model.Student.id]
     * @param picFile 图片文件 [org.springframework.web.multipart.MultipartFile]
     * @throws [com.littlecorgi.courseji.file.exception.FileIsEmptyException] 文件为空时抛出此异常
     * @throws [com.littlecorgi.courseji.student.exception.StudentNotFoundException] 根据studentId找不到数据时抛出此异常
     * @throws [com.tencentcloudapi.common.exception.TencentCloudSDKException] 腾讯云异常信息
     */
    fun compareFaceFromFile(studentId: Long, picFile: MultipartFile): String

    /**
     * 腾讯云/人脸对比，传输图片URL
     *
     * @param studentId 学生id [com.littlecorgi.courseji.student.model.Student.id]
     * @param picURL 图片URL
     * @throws [com.littlecorgi.courseji.student.exception.StudentNotFoundException] 根据studentId找不到数据时抛出此异常
     * @throws [com.tencentcloudapi.common.exception.TencentCloudSDKException] 腾讯云异常信息
     */
    fun compareFaceFromURL(studentId: Long, picURL: String): String

    /**
     * 腾讯云/人脸对比（戴口罩），传输文件
     *
     * @param studentId 学生id [com.littlecorgi.courseji.student.model.Student.id]
     * @param picFile 图片文件 [org.springframework.web.multipart.MultipartFile]
     * @throws [com.littlecorgi.courseji.file.exception.FileIsEmptyException] 文件为空时抛出此异常
     * @throws [com.littlecorgi.courseji.student.exception.StudentNotFoundException] 根据studentId找不到数据时抛出此异常
     * @throws [com.tencentcloudapi.common.exception.TencentCloudSDKException] 腾讯云异常信息
     */
    fun compareMaskFaceFromFile(studentId: Long, picFile: MultipartFile): String

    /**
     * 腾讯云/人脸对比（戴口罩），传输图片URL
     *
     * @param studentId 学生id [com.littlecorgi.courseji.student.model.Student.id]
     * @param picURL 图片URL
     * @throws [com.littlecorgi.courseji.student.exception.StudentNotFoundException] 根据studentId找不到数据时抛出此异常
     * @throws [com.tencentcloudapi.common.exception.TencentCloudSDKException] 腾讯云异常信息
     */
    fun compareMaskFaceFromURL(studentId: Long, picURL: String): String
}