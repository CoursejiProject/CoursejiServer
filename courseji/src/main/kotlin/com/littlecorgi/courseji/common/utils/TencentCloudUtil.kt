package com.littlecorgi.courseji.common.utils

import com.tencentcloudapi.common.Credential
import com.tencentcloudapi.common.profile.ClientProfile
import com.tencentcloudapi.common.profile.HttpProfile
import com.tencentcloudapi.iai.v20200303.IaiClient
import com.tencentcloudapi.iai.v20200303.models.CompareFaceRequest
import com.tencentcloudapi.iai.v20200303.models.CompareFaceResponse
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

    /**
     * 人脸对比
     *
     * @param urlA 图片A
     * @param urlB 图片B
     * @throws [com.tencentcloudapi.common.exception.TencentCloudSDKException] 腾讯云异常信息
     */
    fun compareFace(urlA: String, urlB: String): String {
        val cred = Credential(secretId, secretKey)
        val httpProfile = HttpProfile()
        httpProfile.endpoint = TENCENT_CLOUD_IAI_URL
        val clientProfile = ClientProfile()
        clientProfile.httpProfile = httpProfile
        val client = IaiClient(cred, "ap-guangzhou", clientProfile)
        val req = CompareFaceRequest()
        req.urlA = urlA
        req.urlB = urlB
        val resp: CompareFaceResponse = client.CompareFace(req)
        return CompareFaceResponse.toJsonString(resp)
    }
}