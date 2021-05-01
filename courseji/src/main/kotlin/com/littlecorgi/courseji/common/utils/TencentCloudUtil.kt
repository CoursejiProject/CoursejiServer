package com.littlecorgi.courseji.common.utils

import com.tencentcloudapi.common.Credential
import com.tencentcloudapi.common.profile.ClientProfile
import com.tencentcloudapi.common.profile.HttpProfile
import com.tencentcloudapi.iai.v20200303.IaiClient
import com.tencentcloudapi.iai.v20200303.models.CompareFaceRequest
import com.tencentcloudapi.iai.v20200303.models.CompareFaceResponse
import com.tencentcloudapi.iai.v20200303.models.CompareMaskFaceRequest
import com.tencentcloudapi.iai.v20200303.models.CompareMaskFaceResponse
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment

/**
 * 腾讯云工具类
 * 代码来源：[https://cloud.tencent.com/document/api/867/44978#Java]
 *
 * @author littlecorgi
 * @date 2021/5/1
 */
@Slf4j
object TencentCloudUtil {
    private const val TENCENT_CLOUD_IAI_URL = "iai.tencentcloudapi.com"
    private const val REGION_GUANGZHOU = "ap-guangzhou"

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
     *
     * @param urlA 图片A
     * @param urlB 图片B
     * @throws [com.tencentcloudapi.common.exception.TencentCloudSDKException] 腾讯云异常信息
     */
    fun compareFace(urlA: String, urlB: String): String {
        val req = CompareFaceRequest()
        req.urlA = urlA
        req.urlB = urlB
        val resp: CompareFaceResponse = getRequestClient(REGION_GUANGZHOU).CompareFace(req)
        return CompareFaceResponse.toJsonString(resp)
    }

    /**
     * 人脸对比（戴口罩），没有口罩的需求时最好使用人脸比对标准版
     *
     * @param urlA 图片A
     * @param urlB 图片B
     * @throws [com.tencentcloudapi.common.exception.TencentCloudSDKException] 腾讯云异常信息
     */
    fun compareMaskFace(urlA: String, urlB: String): String {
        val req = CompareMaskFaceRequest()
        val resp = getRequestClient(REGION_GUANGZHOU).CompareMaskFace(req)
        req.urlA = urlA
        req.urlB = urlB
        return CompareMaskFaceResponse.toJsonString(resp)
    }
}