package com.littlecorgi.courseji

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
open class CoursejiApplication

fun main(args: Array<String>) {
    runApplication<CoursejiApplication>(*args)
}
