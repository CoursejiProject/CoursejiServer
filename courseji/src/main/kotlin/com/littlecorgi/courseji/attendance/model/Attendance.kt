package com.littlecorgi.courseji.attendance.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.littlecorgi.courseji.`class`.Class
import com.littlecorgi.courseji.checkon.model.CheckOn
import com.littlecorgi.courseji.common.base.BaseModel
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

/**
 * 考勤信息
 *
 * @author littlecorgi
 * @date 2021/4/21
 */
@Entity
@Table(name = "`attendance`")
@ApiModel(value = "Attendance对象", description = "考勤信息对象")
data class Attendance(

    @Column(nullable = false)
    @ApiModelProperty(value = "签到标题，不为空")
    var title: String = "",

    @Column(nullable = false)
    @ApiModelProperty(value = "签到描述字段，不为空")
    var description: String = "",

    @Column(name = "start_time", nullable = false)
    @ApiModelProperty(value = "签到开始时间，不为空", example = "1")
    var startTime: Long = 0L,

    @Column(name = "end_time", nullable = false)
    @ApiModelProperty(value = "签到结束时间，不为空", example = "1")
    var endTime: Long = 0L,

    @Column(nullable = true)
    @ApiModelProperty(value = "签到定位纬度信息，可为空", example = "1.1")
    var latitude: Double = 0.0,

    @Column(nullable = true)
    @ApiModelProperty(value = "签到定位经度信息，可为空", example = "1.1")
    var longitude: Double = 0.0,

    @ManyToOne(
        cascade = [CascadeType.MERGE, CascadeType.REFRESH], // 级联更新、级联刷新
        optional = false
    )
    // 可选属性optional=false,表示author不能为空。删除文章，不影响用户
    @JoinColumn(name = "class_id") // 设置在attendance表中的关联字段(外键)
    var classDetail: Class = Class(),

    @JsonIgnore
    @OneToMany(
        mappedBy = "attendance",
        cascade = [CascadeType.ALL], // 级联新建、级联删除、级联刷新、级联更新。当删除课程，会级联删除该课程对应的所有签到
        fetch = FetchType.LAZY // 延迟加载
    )
    @ApiModelProperty(value = "该签到的签到纪录，和CheckOn绑定，可为空，创建对象时不添加，需要签到时添加")
    // 拥有mappedBy注解的实体类为关系被维护端
    // mappedBy="attendance"中的attendance是CheckOn中的attendance属性
    var checkOnList: List<CheckOn>? = null
) : BaseModel()
