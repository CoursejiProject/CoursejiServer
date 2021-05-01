package com.littlecorgi.courseji

import com.littlecorgi.courseji.common.utils.QiniuUtils
import com.littlecorgi.courseji.common.utils.TencentCloudUtil
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *
 * @author littlecorgi
 * @date 2021/4/25
 */
@Slf4j
@Configuration
open class InitConfig {

    @Autowired
    private lateinit var appCtx: ApplicationContext
    private val logger = LoggerFactory.getLogger(javaClass)

    @Bean
    open fun init() {
        logger.info("✨开始初始化")
        with(appCtx) {
            QiniuUtils.setAppCtxAndInit(this)
            TencentCloudUtil.setAppCtxAndInit(this)
        }
        logger.info("✨完成初始化")
    }
}
