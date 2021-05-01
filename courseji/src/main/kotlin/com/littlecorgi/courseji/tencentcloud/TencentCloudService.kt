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
     * @param detectLiveFace 是否人脸检测
     * @param usePersonGroup 是否使用腾讯云人员库
     * @param picFile 图片文件 [org.springframework.web.multipart.MultipartFile]
     * @return 一般超过50分则可认定为同一人
     * @throws [com.littlecorgi.courseji.file.exception.FileIsEmptyException] 文件为空时抛出此异常
     * @throws [com.littlecorgi.courseji.student.exception.StudentNotFoundException] 根据studentId找不到数据时抛出此异常
     * @throws [com.tencentcloudapi.common.exception.TencentCloudSDKException] 腾讯云异常信息
     * @throws [com.littlecorgi.courseji.tencentcloud.DetectLiveFaceException] 活体检测失败
     */
    fun compareFaceFromFile(
        studentId: Long,
        detectLiveFace: Boolean,
        usePersonGroup: Boolean,
        picFile: MultipartFile
    ): Float

    /**
     * 腾讯云/人脸对比，传输图片URL
     *
     * @param studentId 学生id [com.littlecorgi.courseji.student.model.Student.id]
     * @param detectLiveFace 是否人脸检测
     * @param usePersonGroup 是否使用腾讯云人员库
     * @param picURL 图片URL
     * @return 一般超过50分则可认定为同一人
     * @throws [com.littlecorgi.courseji.student.exception.StudentNotFoundException] 根据studentId找不到数据时抛出此异常
     * @throws [com.tencentcloudapi.common.exception.TencentCloudSDKException] 腾讯云异常信息
     * @throws [com.littlecorgi.courseji.tencentcloud.DetectLiveFaceException] 活体检测失败
     */
    fun compareFaceFromURL(studentId: Long, detectLiveFace: Boolean, usePersonGroup: Boolean, picURL: String): Float

    /**
     * 腾讯云/人脸对比（戴口罩），传输文件
     *
     * @param studentId 学生id [com.littlecorgi.courseji.student.model.Student.id]
     * @param detectLiveFace 是否人脸检测
     * @param usePersonGroup 是否使用腾讯云人员库
     * @param picFile 图片文件 [org.springframework.web.multipart.MultipartFile]
     * @return 一般超过50分则可认定为同一人
     * @throws [com.littlecorgi.courseji.file.exception.FileIsEmptyException] 文件为空时抛出此异常
     * @throws [com.littlecorgi.courseji.student.exception.StudentNotFoundException] 根据studentId找不到数据时抛出此异常
     * @throws [com.tencentcloudapi.common.exception.TencentCloudSDKException] 腾讯云异常信息
     * @throws [com.littlecorgi.courseji.tencentcloud.DetectLiveFaceException] 活体检测失败
     */
    fun compareMaskFaceFromFile(
        studentId: Long,
        detectLiveFace: Boolean,
        usePersonGroup: Boolean,
        picFile: MultipartFile
    ): Float

    /**
     * 腾讯云/人脸对比（戴口罩），传输图片URL
     *
     * @param studentId 学生id [com.littlecorgi.courseji.student.model.Student.id]
     * @param detectLiveFace 是否人脸检测
     * @param usePersonGroup 是否使用腾讯云人员库
     * @param picURL 图片URL
     * @return 一般超过50分则可认定为同一人
     * @throws [com.littlecorgi.courseji.student.exception.StudentNotFoundException] 根据studentId找不到数据时抛出此异常
     * @throws [com.tencentcloudapi.common.exception.TencentCloudSDKException] 腾讯云异常信息
     * @throws [com.littlecorgi.courseji.tencentcloud.DetectLiveFaceException] 活体检测失败
     */
    fun compareMaskFaceFromURL(studentId: Long, detectLiveFace: Boolean, usePersonGroup: Boolean, picURL: String): Float
}