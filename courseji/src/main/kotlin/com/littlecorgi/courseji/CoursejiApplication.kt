package com.littlecorgi.courseji

import org.jasypt.encryption.StringEncryptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
open class CoursejiApplication : CommandLineRunner {

    @Autowired
    open lateinit var appCtx: ApplicationContext

    @Autowired
    private lateinit var littlecorgiEncryptorBean: StringEncryptor

    override fun run(vararg args: String?) {
        val environment: Environment = appCtx.getBean(Environment::class.java)
        // 首先获取配置文件里的原始明文信息
        val mysqlOriginPswd: String = environment.getProperty("spring.datasource.password") ?: ""
        // 加密
        val mysqlEncryptedPswd: String = encrypt(mysqlOriginPswd)
        // 打印加密前后的结果对比
        println("MySQL原始明文密码为：$mysqlOriginPswd")
        println("====================================")
        println("MySQL原始明文密码加密后的结果为：$mysqlEncryptedPswd")
    }

    private fun encrypt(originPassword: String): String {
        return littlecorgiEncryptorBean.encrypt(originPassword)
    }

    private fun decrypt(encryptedPassword: String): String {
        return littlecorgiEncryptorBean.decrypt(encryptedPassword)
    }
}

fun main(args: Array<String>) {
    runApplication<CoursejiApplication>(*args)
}
