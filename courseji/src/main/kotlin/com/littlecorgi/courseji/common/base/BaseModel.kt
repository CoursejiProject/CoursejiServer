package com.littlecorgi.courseji.common.base

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.annotations.ApiModelProperty
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.format.annotation.DateTimeFormat
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

/**
 * Model的基类
 *
 * @author littlecorgi
 * @date 2021/4/19
 */
@MappedSuperclass
open class BaseModel(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    @ApiModelProperty(value = "数据ID，主键，自增，不为空", example = "1")
    var id: Long = 0L,

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    @Column(name = "create_time", nullable = false)
    @ApiModelProperty(value = "账号创建时间，为时间戳，自动创建，不为空", example = "1")
    var createdTime: Long = 0L,

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    @Column(name = "last_modified_Time", nullable = false)
    @ApiModelProperty(value = "用户信息最近修改时间，为时间戳，自动创建，不为空", example = "1")
    var lastModifiedTime: Long = 0L
)
