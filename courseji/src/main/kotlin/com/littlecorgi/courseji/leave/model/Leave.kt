package com.littlecorgi.courseji.leave.model

import com.littlecorgi.courseji.common.base.BaseModel
import com.littlecorgi.courseji.schedule.model.Schedule
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * 请假的model
 *
 * @author littlecorgi
 * @date 2021/4/24
 */
@Entity
@Table(name = "`leave`")
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

    @Column(nullable = false)
    @ApiModelProperty(value = "第几周，不为空", example = "0")
    var week: Int = 0,

    @Column(nullable = false)
    @ApiModelProperty(value = "周几，不为空", example = "0")
    var day: Int = 0,

    @ManyToOne(
        cascade = [CascadeType.MERGE, CascadeType.REFRESH], // 级联更新、级联刷新
        optional = false
    )
    // 可选属性optional=false,表示student不能为空。删除关联信息，不影响用户
    @JoinColumn(name = "schedule_id") // 设置在schedule表中的关联字段(外键)
    @ApiModelProperty(value = "Schedule主键ID，代表学生和课程的对应关系")
    var schedule: Schedule = Schedule()
) : BaseModel()
