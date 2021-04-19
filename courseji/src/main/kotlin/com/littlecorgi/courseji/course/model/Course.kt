package com.littlecorgi.courseji.course.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.littlecorgi.courseji.common.base.BaseModel
import com.littlecorgi.courseji.common.utils.twoArrayIntersect
import com.littlecorgi.courseji.schedule.model.Schedule
import com.littlecorgi.courseji.teacher.model.Teacher
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
 * 课程信息数据库
 *
 * 字段的含义就是@ApiModelProperty注解中的内容。如果后续删掉这个注解，则记得把注解中内容改到注释中
 *
 * @author littlecorgi
 * @date 2021/4/17
 */
@Entity
@Table(
    name = "course",
    uniqueConstraints = [
        UniqueConstraint(
            name = "unique_course",
            columnNames = ["room", "start_node", "step", "start_week", "end_week", "type"]
        )
    ]
)
@ApiModel(value = "Course对象", description = "课程信息对象")
data class Course(

    @Column(nullable = false)
    @ApiModelProperty(value = "课程名，不为空")
    var name: String = "",

    @Column(nullable = false, length = 1)
    @ApiModelProperty(value = "周几，不为空")
    var day: Int = 0,

    @Column(nullable = false, unique = true)
    @ApiModelProperty(value = "教室名，不为空，不唯一")
    var room: String = "",

    @Column(name = "start_node", nullable = false, unique = true)
    @ApiModelProperty(value = "第几节开始，不为空，不唯一")
    var startNode: Int = 0,

    @Column(nullable = false, unique = true)
    @ApiModelProperty(value = "共几节，不为空，不唯一")
    var step: Int = 0,

    @Column(name = "start_week", nullable = false, unique = true)
    @ApiModelProperty(value = "第几周开始，不为空，不唯一")
    var startWeek: Int = 0,

    @Column(name = "end_week", nullable = false, unique = true)
    @ApiModelProperty(value = "第几周结束，不为空，不唯一")
    var endWeek: Int = 0,

    @Column(nullable = false, unique = true)
    @ApiModelProperty(value = "单周/双周，0每周，1单周，2双周，不为空")
    var type: Int = 0,

    @ManyToOne(
        cascade = [CascadeType.MERGE, CascadeType.REFRESH], // 级联更新、级联刷新
        optional = false
    )
    // 可选属性optional=false,表示author不能为空。删除文章，不影响用户
    @JoinColumn(name = "teacher_id") // 设置在article表中的关联字段(外键)
    var teacher: Teacher = Teacher(),

    @JsonIgnore
    @OneToMany(
        mappedBy = "course",
        cascade = [CascadeType.ALL], // 级联新建、级联删除、级联刷新、级联更新。当删除用户，会级联删除该用户的所有课程
        fetch = FetchType.LAZY // 延迟加载
    )
    @ApiModelProperty(value = "参与课程的学生，和Schedule绑定，可为空，创建对象时不添加，导入课程时添加")
    // 拥有mappedBy注解的实体类为关系被维护端
    // mappedBy="course"中的course是Schedule中的course属性
    var studentList: List<Schedule> = ArrayList()
) : BaseModel() {
    companion object {
        private const val serialVersionUID = 5990939387657237751L
    }

    /**
     * 判断两个Course是否相等：
     *  name、day、room、type必须相等。并且node和week没有交集，否则相等
     */
    override fun equals(other: Any?): Boolean {
        return if (other is Course) {
            if (this.name == other.name && this.day == other.day && this.room == other.room && this.type == other.type) {
                val weekIntersect = twoArrayIntersect(
                    this.startWeek, this.endWeek - this.startWeek,
                    other.startWeek, this.endWeek - this.startWeek
                )
                val nodeIntersect = twoArrayIntersect(
                    this.startNode, this.step,
                    other.startNode, other.step
                )
                !weekIntersect && !nodeIntersect
            } else {
                false
            }
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + day
        result = 31 * result + room.hashCode()
        result = 31 * result + startNode
        result = 31 * result + step
        result = 31 * result + startWeek
        result = 31 * result + endWeek
        result = 31 * result + type
        result = 31 * result + teacher.hashCode()
        return result
    }
}
