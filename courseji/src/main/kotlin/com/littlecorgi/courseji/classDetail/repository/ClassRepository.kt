package com.littlecorgi.courseji.classDetail.repository

import com.littlecorgi.courseji.classDetail.model.Class
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 * @author littlecorgi
 * @date 2021/5/4
 */
interface ClassRepository : JpaRepository<Class, Long> {
}