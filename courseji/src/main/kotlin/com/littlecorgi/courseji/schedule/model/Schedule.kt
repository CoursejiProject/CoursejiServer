package com.littlecorgi.courseji.schedule.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.littlecorgi.courseji.common.base.BaseModel
import com.littlecorgi.courseji.course.model.Course
import com.littlecorgi.courseji.leave.model.Leave
import com.littlecorgi.courseji.student.model.Student
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.UniqueConstraint

/**
 * 上课信息表。
 *  主要是[com.littlecorgi.courseji.student.model.Student]和[com.littlecorgi.courseji.course.model.Course]的对应关系
 *
 * @author littlecorgi
 * @date 2021/4/19
 */
@Entity
@Table(
    name = "`schedule`",
    uniqueConstraints = [
        UniqueConstraint(
            name = "unique_schedule",
            columnNames = ["student_id", "course_id"]
        )
    ]
)
@ApiModel(value = "Schedule对象", description = "课程学生绑定，用来让课程和学生对应起来，代表学生上这门课")
data class Schedule(

    @ManyToOne(
        cascade = [CascadeType.MERGE, CascadeType.REFRESH], // 级联更新、级联刷新
        optional = false
    )
    // 可选属性optional=false,表示student不能为空。删除关联信息，不影响用户
    @JoinColumn(name = "student_id") // 设置在schedule表中的关联字段(外键)
    @ApiModelProperty(value = "学生主键ID")
    var student: Student = Student(),

    @ManyToOne(
        cascade = [CascadeType.MERGE, CascadeType.REFRESH], // 级联更新、级联刷新
        optional = false
    )
    // 可选属性optional=false,表示course不能为空。删除管理信息，不影响课程
    @JoinColumn(name = "course_id") // 设置在schedule表中的关联字段(外键)
    @ApiModelProperty(value = "课程主键ID")
    var course: Course = Course(),

    @JsonIgnore
    @Column(nullable = true)
    @OneToMany(
        mappedBy = "schedule",
        cascade = [CascadeType.ALL], // 级联新建、级联删除、级联刷新、级联更新。当删除用户，会级联删除该用户的所有课程
        fetch = FetchType.LAZY // 延迟加载
    )
    @ApiModelProperty(value = "学生申请的请假，和Leave绑定，可为空，创建对象时不添加，创建请假时添加")
    // 拥有mappedBy注解的实体类为关系被维护端
    // mappedBy="student"中的schedule是Leave中的schedule属性
    var leaveList: List<Leave>? = null
) : BaseModel()
