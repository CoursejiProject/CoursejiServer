package com.littlecorgi.courseji

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.web.servlet.DispatcherServlet

@SpringBootApplication
@EnableJpaAuditing
open class CoursejiApplication : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
    }

    @Bean
    open fun dispatcherRegistration(): ServletRegistrationBean<*>? {
        return ServletRegistrationBean(dispatcherServlet())
    }

    @Bean(name = [DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME])
    open fun dispatcherServlet(): DispatcherServlet? {
        return LoggableDispatcherServlet()
    }
}

fun main(args: Array<String>) {
    runApplication<CoursejiApplication>(*args)
}
