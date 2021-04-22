package com.littlecorgi.courseji.checkon.model

import com.littlecorgi.courseji.attendance.model.Attendance
import com.littlecorgi.courseji.common.base.BaseModel
import com.littlecorgi.courseji.student.model.Student
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.UniqueConstraint

/**
 * 参与签到信息，主要是[com.littlecorgi.courseji.attendance.model.Attendance]和[com.littlecorgi.courseji.student.model.Student]的对应关系
 *
 * <p/>
 * @author littlecorgi
 * @date 2021/4/22
 */
@Entity
@Table(
    name = "check_on",
    uniqueConstraints = [
        UniqueConstraint(
            name = "unique_check_on",
            columnNames = ["student_id", "attendance_id"]
        )
    ]
)
@ApiModel(value = "CheckOn对象", description = "签到学生绑定，用来让签到和学生对应起来，代表学生进行了签到")
data class CheckOn(

    @Column(nullable = false)
    @ApiModelProperty(value = "签到定位纬度信息，不为空，0为未签，1为已签，2为请假，3为定位超出范围", example = "0")
    var checkOnStates: Int = 0,

    @Column(nullable = true)
    @ApiModelProperty(value = "签到定位经度信息，可为空", example = "0.0F")
    var longitude: Float = 0.0F,

    @Column(nullable = true)
    @ApiModelProperty(value = "签到定位纬度信息，可为空", example = "0.0F")
    var latitude: Float = 0.0F,

    @ManyToOne(
        cascade = [CascadeType.MERGE, CascadeType.REFRESH], // 级联更新、级联刷新
        optional = false
    )
    // 可选属性optional=false,表示student不能为空。删除关联信息，不影响用户
    @JoinColumn(name = "student_id") // 设置在user表中的关联字段(外键)
    @ApiModelProperty(value = "学生主键ID")
    var student: Student = Student(),

    @ManyToOne(
        cascade = [CascadeType.MERGE, CascadeType.REFRESH], // 级联更新、级联刷新
        optional = false
    )
    // 可选属性optional=false,表示course不能为空。删除管理信息，不影响课程
    @JoinColumn(name = "attendance_id") // 设置在course表中的关联字段(外键)
    @ApiModelProperty(value = "签到主键ID")
    var attendance: Attendance = Attendance()
) : BaseModel()
