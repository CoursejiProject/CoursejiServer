package com.littlecorgi.courseji.common.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.littlecorgi.courseji.file.exception.FileIsEmptyException
import com.qiniu.http.Response
import com.qiniu.storage.Configuration
import com.qiniu.storage.Region
import com.qiniu.storage.UploadManager
import com.qiniu.storage.model.DefaultPutRet
import com.qiniu.util.Auth
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment
import org.springframework.web.multipart.MultipartFile

/**
 *
 * @author littlecorgi
 * @date 2021/4/25
 */
@Slf4j
object QiniuUtils {

    private val logger = LoggerFactory.getLogger(javaClass)

    // 构造一个带指定 Region 对象的配置类
    private val cfg = Configuration(Region.huanan())

    // 其他参数参考类注释
    private val uploadManager = UploadManager(cfg)
    private lateinit var environment: Environment

    // 生成上传凭证，然后准备上传
    private lateinit var accessKey: String
    private lateinit var secretKey: String
    private lateinit var bucket: String

    private lateinit var auth: Auth
    private lateinit var upToken: String

    fun setAppCtxAndInit(appCtx: ApplicationContext) {
        logger.info("QiniuUtils:⚡️️初始化开始")
        environment = appCtx.getBean(Environment::class.java)
        accessKey = environment.getProperty("qiniu.accessKey") ?: ""
        secretKey = environment.getProperty("qiniu.secretKey") ?: ""
        bucket = environment.getProperty("qiniu.bucket") ?: ""

        auth = Auth.create(accessKey, secretKey)
        upToken = auth.uploadToken(bucket)
        logger.info("QiniuUtils:☀️初始化完成")
    }

    /**
     * 上传图片
     *
     * @param picFile 图片文件
     * @return 上传后的api
     */
    fun uploadPic(picFile: MultipartFile): String {
        if (picFile.isEmpty) throw FileIsEmptyException()
        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        // 文件名设置为courseji/pic前缀并加上文件的hashCode
        if (picFile.contentType == null) throw FileIsEmptyException()
        val type = picFile.contentType!!.split("/").last()
        val key = "courseji/pic/${picFile.hashCode()}.$type"
        val response: Response = uploadManager.put(picFile.bytes, key, upToken)
        // 解析上传成功的结果
        val objectMapper = ObjectMapper()
        val putRet = objectMapper.readValue(response.bodyString(), DefaultPutRet::class.java)
        // 拼接上阿里云CDN域名
        logger.info("hash= ${putRet.hash}")
        logger.info("key= ${putRet.key}")
        return "https://cdn.littlecorgi.top/${putRet.key}"
    }
}
