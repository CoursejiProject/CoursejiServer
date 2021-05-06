package com.littlecorgi.courseji.classDetail.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.littlecorgi.courseji.attendance.model.Attendance
import com.littlecorgi.courseji.classandstudent.model.ClassAndStudent
import com.littlecorgi.courseji.common.base.BaseModel
import com.littlecorgi.courseji.common.constants.UserDataConstants
import com.littlecorgi.courseji.leave.model.Leave
import com.littlecorgi.courseji.teacher.model.Teacher
import io.swagger.annotations.ApiModelProperty
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

/**
 *
 * @author littlecorgi
 * @date 2021/5/4
 */
@Entity
data class Class(

    @Column(nullable = false, length = UserDataConstants.NAME_MAX_LENGTH)
    @ApiModelProperty(value = "班级姓名，不许空", required = true)
    var name: String = "", // 班级姓名，不许空

    @Column(nullable = false)
    @ApiModelProperty(value = "学生数，不许空", required = true)
    var studentNum: Int = 0, // 学生数，不许空

    @ManyToOne(
        cascade = [CascadeType.MERGE, CascadeType.REFRESH], // 级联更新、级联刷新
        optional = false
    )
    // 可选属性optional=false,表示author不能为空。删除文章，不影响用户
    @JoinColumn(name = "teacher_id") // 设置在course表中的关联字段(外键)
    var teacher: Teacher = Teacher(),

    @JsonIgnore
    @Column(nullable = true)
    @OneToMany(
        mappedBy = "classDetail",
        cascade = [CascadeType.ALL], // 级联新建、级联删除、级联刷新、级联更新。当删除用户，会级联删除该用户的所有课程
        fetch = FetchType.LAZY // 延迟加载
    )
    @ApiModelProperty(value = "学生加入的班级，和Student绑定，可为空，创建对象时不添加，参加签到时添加")
    // 拥有mappedBy注解的实体类为关系被维护端
    // mappedBy="student"中的classDetail是ClassAndStudent中的classDetail属性
    var classAndStudentList: List<ClassAndStudent>? = null,

    @JsonIgnore
    @Column(nullable = true)
    @OneToMany(
        mappedBy = "classDetail",
        cascade = [CascadeType.ALL], // 级联新建、级联删除、级联刷新、级联更新。当删除用户，会级联删除该用户的所有课程
        fetch = FetchType.LAZY // 延迟加载
    )
    @ApiModelProperty(value = "学生加入的班级，和Student绑定，可为空，创建对象时不添加，参加签到时添加")
    // 拥有mappedBy注解的实体类为关系被维护端
    // mappedBy="student"中的classDetail是Attendance中的classDetail属性
    var attendanceList: List<Attendance>? = null,

    @JsonIgnore
    @Column(nullable = true)
    @OneToMany(
        mappedBy = "classDetail",
        cascade = [CascadeType.ALL], // 级联新建、级联删除、级联刷新、级联更新。当删除用户，会级联删除该用户的所有课程
        fetch = FetchType.LAZY // 延迟加载
    )
    @ApiModelProperty(value = "此班级的所有请假，和Leave绑定，可为空，创建对象时不添加，参加签到时添加")
    // 拥有mappedBy注解的实体类为关系被维护端
    // mappedBy="student"中的classDetail是Attendance中的classDetail属性
    var leaveList: List<Leave>? = null
) : BaseModel()
