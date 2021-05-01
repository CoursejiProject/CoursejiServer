package com.littlecorgi.courseji.common.utils

import com.littlecorgi.courseji.common.tencentcloud.TencentCloudConstant.REGION_GUANGZHOU
import com.littlecorgi.courseji.common.tencentcloud.TencentCloudConstant.TENCENT_CLOUD_IAI_URL
import com.littlecorgi.courseji.student.model.Student
import com.tencentcloudapi.common.Credential
import com.tencentcloudapi.common.profile.ClientProfile
import com.tencentcloudapi.common.profile.HttpProfile
import com.tencentcloudapi.iai.v20200303.IaiClient
import com.tencentcloudapi.iai.v20200303.models.CompareFaceRequest
import com.tencentcloudapi.iai.v20200303.models.CompareFaceResponse
import com.tencentcloudapi.iai.v20200303.models.CompareMaskFaceRequest
import com.tencentcloudapi.iai.v20200303.models.CompareMaskFaceResponse
import com.tencentcloudapi.iai.v20200303.models.CreatePersonRequest
import com.tencentcloudapi.iai.v20200303.models.CreatePersonResponse
import com.tencentcloudapi.iai.v20200303.models.DeletePersonRequest
import com.tencentcloudapi.iai.v20200303.models.DeletePersonResponse
import com.tencentcloudapi.iai.v20200303.models.DetectLiveFaceAccurateRequest
import com.tencentcloudapi.iai.v20200303.models.DetectLiveFaceAccurateResponse
import com.tencentcloudapi.iai.v20200303.models.PersonExDescriptionInfo
import com.tencentcloudapi.iai.v20200303.models.VerifyFaceRequest
import com.tencentcloudapi.iai.v20200303.models.VerifyFaceResponse
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment

/**
 * 腾讯云工具类
 *
 * @author littlecorgi
 * @date 2021/5/1
 */
@Slf4j
object TencentCloudUtil {
    private val logger = LoggerFactory.getLogger(javaClass)
    private lateinit var environment: Environment
    private lateinit var secretId: String
    private lateinit var secretKey: String

    fun setAppCtxAndInit(appCtx: ApplicationContext) {
        logger.info("TencentCloudUtil:⚡️️初始化开始")
        environment = appCtx.getBean(Environment::class.java)
        secretId = environment.getProperty("tencent-cloud.secret-id") ?: ""
        secretKey = environment.getProperty("tencent-cloud.secret-key") ?: ""
        logger.info("TencentCloudUtil:☀️初始化完成")
    }

    private fun getRequestClient(region: String) = run {
        val cred = Credential(secretId, secretKey)
        val httpProfile = HttpProfile()
        httpProfile.endpoint = TENCENT_CLOUD_IAI_URL
        val clientProfile = ClientProfile()
        clientProfile.httpProfile = httpProfile
        IaiClient(cred, region, clientProfile)
    }

    /**
     * 人脸对比
     * [代码来源](https://console.cloud.tencent.com/api/explorer?Product=iai&Version=2020-03-03&Action=CompareFace)
     *
     * @param urlA 图片A
     * @param urlB 图片B
     * @return [com.tencentcloudapi.iai.v20200303.models.CompareFaceResponse] 人脸对比结果
     * @throws [com.tencentcloudapi.common.exception.TencentCloudSDKException] 腾讯云异常信息
     */
    fun compareFace(urlA: String, urlB: String): CompareFaceResponse {
        val req = CompareFaceRequest()
        req.urlA = urlA
        req.urlB = urlB
        return getRequestClient(REGION_GUANGZHOU).CompareFace(req)
    }

    /**
     * 人脸对比（戴口罩），没有口罩的需求时最好使用人脸比对标准版
     * [代码来源](https://console.cloud.tencent.com/api/explorer?Product=iai&Version=2020-03-03&Action=CompareMaskFace&)
     *
     * @param urlA 图片A
     * @param urlB 图片B
     * @return [com.tencentcloudapi.iai.v20200303.models.CompareMaskFaceResponse] 人脸对比（戴口罩）结果
     * @throws [com.tencentcloudapi.common.exception.TencentCloudSDKException] 腾讯云异常信息
     */
    fun compareMaskFace(urlA: String, urlB: String): CompareMaskFaceResponse {
        val req = CompareMaskFaceRequest()
        req.urlA = urlA
        req.urlB = urlB
        return getRequestClient(REGION_GUANGZHOU).CompareMaskFace(req)
    }

    /**
     * 人脸静态活体检测（高精度版）
     * [代码来源](https://console.cloud.tencent.com/api/explorer?Product=iai&Version=2020-03-03&Action=DetectLiveFaceAccurate)
     *
     * @param url 图片URL
     * @return [com.tencentcloudapi.iai.v20200303.models.DetectLiveFaceAccurateResponse] 人脸静态活体检测（高精度版）结果
     * @throws [com.tencentcloudapi.common.exception.TencentCloudSDKException] 腾讯云异常信息
     */
    fun detectLiveFaceAccurate(url: String): DetectLiveFaceAccurateResponse {
        val req = DetectLiveFaceAccurateRequest()
        req.url = url
        return getRequestClient(REGION_GUANGZHOU).DetectLiveFaceAccurate(req)
    }

    /**
     * 创建人员库人员信息
     * [代码来源](https://console.cloud.tencent.com/api/explorer?Product=iai&Version=2020-03-03&Action=CreatePerson)
     *
     * @param student 学生信息 [com.littlecorgi.courseji.student.model.Student]
     * @return [com.tencentcloudapi.iai.v20200303.models.CreatePersonResponse] 创建人员结果
     * @throws [com.tencentcloudapi.common.exception.TencentCloudSDKException] 腾讯云异常信息
     */
    fun createPerson(student: Student): CreatePersonResponse {
        val req = CreatePersonRequest().apply {
            this.groupId = "courseji-student"
            this.personName = student.name
            this.personId = student.id.toString()

            this.personExDescriptionInfos = arrayOf(
                PersonExDescriptionInfo().also {
                    it.personExDescriptionIndex = 1L
                    it.personExDescription = student.phone
                },
                PersonExDescriptionInfo().also {
                    it.personExDescriptionIndex = 2L
                    it.personExDescription = student.email
                }
            )

            this.url = student.picture
            this.uniquePersonControl = 1L
            this.qualityControl = 2L
        }
        return getRequestClient(REGION_GUANGZHOU).CreatePerson(req)
    }

    /**
     * 删除人员库人员信息
     * [代码来源](https://console.cloud.tencent.com/api/explorer?Product=iai&Version=2020-03-03&Action=DeletePerson)
     *
     * @param student 学生信息 [com.littlecorgi.courseji.student.model.Student]
     * @return [com.tencentcloudapi.iai.v20200303.models.DeletePersonResponse] 删除人员结果
     * @throws [com.tencentcloudapi.common.exception.TencentCloudSDKException] 腾讯云异常信息
     */
    fun deletePerson(student: Student): DeletePersonResponse {
        val req = DeletePersonRequest()
        req.personId = student.id.toString()
        return getRequestClient(REGION_GUANGZHOU).DeletePerson(req)
    }

    /**
     * 创建人员库人员信息
     * [代码来源](https://console.cloud.tencent.com/api/explorer?Product=iai&Version=2020-03-03&Action=VerifyFace)
     *
     * @param studentId 学生信息ID [com.littlecorgi.courseji.student.model.Student.id]
     * @param picUrl 人脸图片URL
     * @return [com.tencentcloudapi.iai.v20200303.models.VerifyFaceResponse] 人脸验证结果
     * @throws [com.tencentcloudapi.common.exception.TencentCloudSDKException] 腾讯云异常信息
     */
    fun verifyFace(studentId: Long, picUrl: String): VerifyFaceResponse {
        val req = VerifyFaceRequest()
        req.url = picUrl
        req.personId = studentId.toString()
        return getRequestClient(REGION_GUANGZHOU).VerifyFace(req)
    }
}