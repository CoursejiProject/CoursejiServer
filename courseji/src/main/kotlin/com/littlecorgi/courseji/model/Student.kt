package com.littlecorgi.courseji.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * 学生用户数据库表
 * @author littlecorgi
 * @date 2021/4/12
 */
@Entity
class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Int = 0 // 学生用户ID
}