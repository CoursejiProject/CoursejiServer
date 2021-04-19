package com.littlecorgi.courseji.course.module

import com.fasterxml.jackson.annotation.JsonIgnore
import com.littlecorgi.courseji.common.utils.twoArrayIntersect
import com.littlecorgi.courseji.user.model.User
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.format.annotation.DateTimeFormat
import java.sql.Date
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
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
    uniqueConstraints = [UniqueConstraint(
        name = "unique_course",
        columnNames = ["room", "start_node", "step", "start_week", "end_week", "type"]
    )]
)
@ApiModel(value = "Course对象", description = "课程信息对象")
data class Course(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    @ApiModelProperty(value = "课程ID，主键，自增，不为空")
    var id: Long = 0,

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    @Column(name = "create_time", nullable = false)
    @ApiModelProperty(value = "账号创建时间，为时间戳，自动创建，不为空")
    var createdTime: Long = 0L,  // 账号创建时间，自动创建，不为空

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    @Column(name = "last_modified_Time", nullable = false)
    @ApiModelProperty(value = "用户信息最近修改时间，为时间戳，自动创建，不为空")
    var lastModifiedTime: Long = 0L,  // 用户信息最近修改时间，自动创建，不为空

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
    //可选属性optional=false,表示author不能为空。删除文章，不影响用户
    @JoinColumn(name = "teacher_id")//设置在article表中的关联字段(外键)
    var teacher: User = User()
) {
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