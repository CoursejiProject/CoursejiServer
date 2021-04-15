package com.littlecorgi.courseji.user.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.littlecorgi.courseji.common.UserDataConstants
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.springframework.data.annotation.CreatedDate
import java.sql.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

/**
 * 用户数据库表
 * @author littlecorgi
 * @date 2021/4/12
 */
@Entity
@Table(name = "user")
@ApiModel(value = "User对象", description = "教师和学生用户对象")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    @ApiModelProperty(value = "学生用户ID，主键，自增，不为空")
    var id: Long = 0, // 学生用户ID，主键，自增，不为空

    @CreatedDate
    @JsonIgnore
    @Column(name = "date", nullable = false)
    @ApiModelProperty(value = "账号创建时间，自动创建")
    var date: Date = Date.valueOf("2021-01-01"),  // 账号创建时间，自动创建

    @Column(nullable = false, length = UserDataConstants.NAME_MAX_LENGTH)
    @ApiModelProperty(value = "学生姓名，不许空", required = true)
    var name: String = "", // 学生姓名，不许空

    @Column(unique = true, nullable = false, length = UserDataConstants.EMAIL_MAX_LENGTH)
    @ApiModelProperty(value = "邮箱，字段不允许重复，不许空，最长50字符", required = true)
    var email: String = "", // 邮箱，字段不允许重复，不许空，最长50字符

    @Column(nullable = false, length = UserDataConstants.PASSWORD_MAX_LENGTH)
    @ApiModelProperty(value = "密码，不许空，最长20字符", required = true)
    var password: String = "", // 密码，不许空，最长20字符

    @Column(unique = true, nullable = false, length = UserDataConstants.PHONE_LENGTH)
    @ApiModelProperty(value = "手机号，不许空，最长11字符", required = true)
    var phone: String = "", // 手机号，不许空，最长11字符

    @Column(name = "is_teacher", nullable = false)
    @ApiModelProperty(value = "是否是教师，不许空", required = true, name = "isTeacher")
    @JsonProperty(value = "isTeacher") // Kotlin中好像不起作用
    var isTeacher: Boolean = false, // 是否是教师，不许空

    @Column(nullable = false)
    @ApiModelProperty(value = "用户头像url，不许空", required = true)
    var avatar: String = "", // 用户头像url，不许空

    @Column(nullable = false)
    @ApiModelProperty(value = "用户照片，用于人脸识别，不许空", required = true)
    var picture: String = "" // 用户照片，用于人脸识别，不许空
)