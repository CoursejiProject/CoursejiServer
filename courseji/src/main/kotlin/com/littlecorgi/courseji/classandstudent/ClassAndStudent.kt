package com.littlecorgi.courseji.classandstudent

import com.littlecorgi.courseji.classDetail.model.Class
import com.littlecorgi.courseji.common.base.BaseModel
import com.littlecorgi.courseji.student.model.Student
import io.swagger.annotations.ApiModelProperty
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 *
 * @author littlecorgi
 * @date 2021/5/4
 */
@Entity
@Table(name = "class_and_student")
data class ClassAndStudent(

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
    @JoinColumn(name = "class_id") // 设置在schedule表中的关联字段(外键)
    @ApiModelProperty(value = "班级主键ID")
    var classDetail: Class = Class(),
) : BaseModel()
