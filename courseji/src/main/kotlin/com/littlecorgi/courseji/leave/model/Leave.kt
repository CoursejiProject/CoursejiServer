package com.littlecorgi.courseji.leave.model

import com.littlecorgi.courseji.common.base.BaseModel
import com.littlecorgi.courseji.course.model.Course
import com.littlecorgi.courseji.student.model.Student
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.UniqueConstraint

/**
 * 请假的model
 *
 * @author littlecorgi
 * @date 2021/4/24
 */
@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(
    name = "leave",
    uniqueConstraints = [
        UniqueConstraint(
            name = "unique_leave",
            columnNames = ["student", "course", "week", "day"]
        )
    ]
)
@ApiModel(value = "Leave对象", description = "请假信息对象")
data class Leave(

    @Column(nullable = false)
    @ApiModelProperty(value = "请假状态，0-学生提交申请，1-教师拒绝，2-教师同意，不为空", example = "0")
    var states: Int = 0,

    @Column(nullable = false)
    @ApiModelProperty(value = "标题，不为空")
    var title: String = "",

    @Column(nullable = false)
    @ApiModelProperty(value = "描述，不为空")
    var description: String = "",

    @Column(nullable = true)
    @ApiModelProperty(value = "教师审批意见，可为空")
    var opinion: String = "",

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

    @Column(nullable = false)
    @ApiModelProperty(value = "第几周，不为空", example = "0")
    var week: Int = 0,

    @Column(nullable = false)
    @ApiModelProperty(value = "周几，不为空", example = "0")
    var day: Int = 0
) : BaseModel()
