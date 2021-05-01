package com.littlecorgi.courseji.common.tencentcloud

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * 腾讯云返回结果
 *
 * @author littlecorgi
 * @date 2021/5/1
 */
@ApiModel
data class TencentCloudResponse(
    /**
     * 人脸识别
     * - 两张图片中人脸的相似度分数。
     * - 不同算法版本返回的相似度分数不同。
     * - 若需要验证两张图片中人脸是否为同一人，3.0版本误识率千分之一对应分数为40分，误识率万分之一对应分数为50分，误识率十万分之一对应分数为60分。 一般超过50分则可认定为同一人。
     *
     * 活体打分
     * - 取值范围 [0,100]，推荐阈值为40
     */
    @ApiModelProperty(value = "活体打分，取值范围 [0,100]", required = true, example = "1.1")
    val score: Float,

    @ApiModelProperty(value = "人脸识别所用的算法模型版本", required = true)
    val faceModelVersion: String,

    @ApiModelProperty(value = "唯一请求 ID", required = true)
    val requestId: String
)
