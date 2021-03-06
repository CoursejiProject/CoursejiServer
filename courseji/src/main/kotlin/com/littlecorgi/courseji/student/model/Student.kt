package com.littlecorgi.courseji.student.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.littlecorgi.courseji.checkon.model.CheckOn
import com.littlecorgi.courseji.classandstudent.model.ClassAndStudent
import com.littlecorgi.courseji.common.base.BaseModel
import com.littlecorgi.courseji.common.constants.UserDataConstants
import com.littlecorgi.courseji.leave.model.Leave
import com.littlecorgi.courseji.schedule.model.Schedule
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany
import javax.persistence.Table

/**
 * 学生数据库表
 *
 * 字段的含义就是@ApiModelProperty注解中的内容。如果后续删掉这个注解，则记得把注解中内容改到注释
 *
 * 此库中主键id兼任腾讯云人脸识别人员库中的PersonID
 *
 * @author littlecorgi
 * @date 2021/4/12
 */
@Entity

@Table(name = "student")
@ApiModel(value = "Student对象", description = "学生用户对象")
data class Student(
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

    @Column(nullable = false)
    @ApiModelProperty(value = "用户头像url，不许空", required = true)
    var avatar: String = "", // 用户头像url，不许空

    @Column(nullable = false)
    @ApiModelProperty(value = "用户照片，用于人脸识别，不许空", required = true)
    var picture: String = "", // 用户照片，用于人脸识别，不许空

    @JsonIgnore
    @Column(nullable = true)
    @OneToMany(
        mappedBy = "student",
        cascade = [CascadeType.ALL], // 级联新建、级联删除、级联刷新、级联更新。当删除用户，会级联删除该用户的所有课程
        fetch = FetchType.LAZY // 延迟加载
    )
    @ApiModelProperty(value = "学生参加的课程，和Schedule绑定，可为空，创建对象时不添加，导入课程时添加")
    // 拥有mappedBy注解的实体类为关系被维护端
    // mappedBy="student"中的student是Schedule中的student属性
    var scheduleList: List<Schedule>? = null,

    @JsonIgnore
    @Column(nullable = true)
    @OneToMany(
        mappedBy = "student",
        cascade = [CascadeType.ALL], // 级联新建、级联删除、级联刷新、级联更新。当删除用户，会级联删除该用户的所有课程
        fetch = FetchType.LAZY // 延迟加载
    )
    @ApiModelProperty(value = "学生参加的签到，和Attendance绑定，可为空，创建对象时不添加，参加签到时添加")
    // 拥有mappedBy注解的实体类为关系被维护端
    // mappedBy="student"中的student是CheckOn中的student属性
    var checkOnList: List<CheckOn>? = null,

    @JsonIgnore
    @Column(nullable = true)
    @OneToMany(
        mappedBy = "student",
        cascade = [CascadeType.ALL], // 级联新建、级联删除、级联刷新、级联更新。当删除用户，会级联删除该用户的所有课程
        fetch = FetchType.LAZY // 延迟加载
    )
    @ApiModelProperty(value = "学生加入的班级，和Class绑定，可为空，创建对象时不添加，参加签到时添加")
    // 拥有mappedBy注解的实体类为关系被维护端
    // mappedBy="student"中的student是ClassAndStudent中的student属性
    var classAndStudentList: List<ClassAndStudent>? = null,

    @JsonIgnore
    @Column(nullable = true)
    @OneToMany(
        mappedBy = "student",
        cascade = [CascadeType.ALL], // 级联新建、级联删除、级联刷新、级联更新。当删除用户，会级联删除该用户的所有课程
        fetch = FetchType.LAZY // 延迟加载
    )
    @ApiModelProperty(value = "学生加入的班级，和Class绑定，可为空，创建对象时不添加，参加签到时添加")
    // 拥有mappedBy注解的实体类为关系被维护端
    // mappedBy="student"中的student是Leave中的student属性
    var leaveList: List<Leave>? = null
) : BaseModel() {
    companion object {
        private const val serialVersionUID = 5990939387657237750L
    }
}
