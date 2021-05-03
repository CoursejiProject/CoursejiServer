package com.littlecorgi.courseji.tencentcloud

import com.littlecorgi.courseji.common.ResponseCode
import com.littlecorgi.courseji.common.ServerResponse
import com.littlecorgi.courseji.file.exception.FileIsEmptyException
import com.littlecorgi.courseji.student.exception.StudentNotFoundException
import com.tencentcloudapi.common.exception.TencentCloudSDKException
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

/**
 *
 * @author littlecorgi
 * @date 2021/4/30
 */
@Api(value = "腾讯云相关")
@Slf4j
@RestController
@RequestMapping(path = ["/tencentCloud"])
class TencentCloudController {

    @Autowired
    private lateinit var tencentCloudService: TencentCloudService

    private val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping(path = ["/compareFaceFromFile"])
    @ApiOperation(value = "腾讯云/人脸对比，传输文件")
    fun compareFaceFromFile(
        @ApiParam(value = "用户id", required = true, defaultValue = "1") @RequestParam studentId: Long,
        @ApiParam(value = "是否活体检测", required = true) @RequestParam detectLiveFace: Boolean,
        @ApiParam(value = "是否使用腾讯云人员库", required = true) @RequestParam usePersonGroup: Boolean,
        @ApiParam(value = "用户上传的人脸图片的文件", required = true) @RequestBody picFile: MultipartFile
    ): ServerResponse<Float> =
        try {
            ServerResponse.createBySuccess(
                tencentCloudService.compareFaceFromFile(
                    studentId,
                    detectLiveFace,
                    usePersonGroup,
                    picFile
                )
            )
        } catch (e: StudentNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_USER)
        } catch (e: FileIsEmptyException) {
            ServerResponse.createByFailure(ResponseCode.FILE_IS_EMPTY, errorMsg = e.message)
        } catch (e: TencentCloudSDKException) {
            logger.error("腾讯云/人脸对比，传输文件（腾讯云报错信息）:", e.printStackTrace())
            ServerResponse.createByFailure(ResponseCode.TENCENT_CLOUD_EXCEPTION, errorMsg = e.message)
        } catch (e: DetectLiveFaceException) {
            ServerResponse.createByFailure(ResponseCode.DETECT_LIVE_FACE_FAILURE, errorMsg = e.message)
        } catch (e: Exception) {
            logger.error("腾讯云/人脸对比，传输文件:", e.printStackTrace())
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }

    @PostMapping(path = ["/compareFaceFromURL"])
    @ApiOperation(value = "腾讯云/人脸对比，传输图片URL")
    fun compareFaceFromURL(
        @ApiParam(value = "用户id", required = true, defaultValue = "1") @RequestParam studentId: Long,
        @ApiParam(value = "是否活体检测", required = true) @RequestParam detectLiveFace: Boolean,
        @ApiParam(value = "是否使用腾讯云人员库", required = true) @RequestParam usePersonGroup: Boolean,
        @ApiParam(value = "用户上传的人脸图片的URL", required = true) @RequestBody picURL: String
    ): ServerResponse<Float> =
        try {
            ServerResponse.createBySuccess(
                tencentCloudService.compareFaceFromURL(
                    studentId,
                    detectLiveFace,
                    usePersonGroup,
                    picURL
                )
            )
        } catch (e: StudentNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_USER)
        } catch (e: DetectLiveFaceException) {
            ServerResponse.createByFailure(ResponseCode.DETECT_LIVE_FACE_FAILURE, errorMsg = e.message)
        } catch (e: TencentCloudSDKException) {
            logger.error("腾讯云/人脸对比，传输图片URL（腾讯云报错信息）:", e.printStackTrace())
            ServerResponse.createByFailure(ResponseCode.TENCENT_CLOUD_EXCEPTION, errorMsg = e.message)
        } catch (e: Exception) {
            logger.error("腾讯云/人脸对比，传输图片URL:", e.printStackTrace())
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }

    @PostMapping(path = ["/compareMaskFaceFromFile"])
    @ApiOperation(value = "腾讯云/人脸对比（戴口罩），传输文件")
    fun compareMaskFaceFromFile(
        @ApiParam(value = "用户id", required = true, defaultValue = "1") @RequestParam studentId: Long,
        @ApiParam(value = "是否活体检测", required = true) @RequestParam detectLiveFace: Boolean,
        @ApiParam(value = "是否使用腾讯云人员库", required = true) @RequestParam usePersonGroup: Boolean,
        @ApiParam(value = "用户上传的人脸图片的文件", required = true) @RequestBody picFile: MultipartFile
    ): ServerResponse<Float> =
        try {
            ServerResponse.createBySuccess(
                tencentCloudService.compareMaskFaceFromFile(
                    studentId,
                    detectLiveFace,
                    usePersonGroup,
                    picFile
                )
            )
        } catch (e: StudentNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_USER)
        } catch (e: FileIsEmptyException) {
            ServerResponse.createByFailure(ResponseCode.FILE_IS_EMPTY, errorMsg = e.message)
        } catch (e: DetectLiveFaceException) {
            ServerResponse.createByFailure(ResponseCode.DETECT_LIVE_FACE_FAILURE, errorMsg = e.message)
        } catch (e: TencentCloudSDKException) {
            logger.error("腾讯云/人脸对比，传输文件（腾讯云报错信息）:", e.printStackTrace())
            ServerResponse.createByFailure(ResponseCode.TENCENT_CLOUD_EXCEPTION, errorMsg = e.message)
        } catch (e: Exception) {
            logger.error("腾讯云/人脸对比，传输文件:", e.printStackTrace())
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }

    @PostMapping(path = ["/compareMaskFaceFromURL"])
    @ApiOperation(value = "腾讯云/人脸对比（戴口罩），传输图片URL")
    fun compareMaskFaceFromURL(
        @ApiParam(value = "用户id", required = true, defaultValue = "1") @RequestParam studentId: Long,
        @ApiParam(value = "是否活体检测", required = true) @RequestParam detectLiveFace: Boolean,
        @ApiParam(value = "是否使用腾讯云人员库", required = true) @RequestParam usePersonGroup: Boolean,
        @ApiParam(value = "用户上传的人脸图片的URL", required = true) @RequestBody picURL: String
    ): ServerResponse<Float> =
        try {
            ServerResponse.createBySuccess(
                tencentCloudService.compareMaskFaceFromURL(
                    studentId,
                    detectLiveFace,
                    usePersonGroup,
                    picURL
                )
            )
        } catch (e: StudentNotFoundException) {
            ServerResponse.createByFailure(ResponseCode.NO_USER)
        } catch (e: DetectLiveFaceException) {
            ServerResponse.createByFailure(ResponseCode.DETECT_LIVE_FACE_FAILURE, errorMsg = e.message)
        } catch (e: TencentCloudSDKException) {
            logger.error("腾讯云/人脸对比，传输图片URL（腾讯云报错信息）:", e.printStackTrace())
            ServerResponse.createByFailure(ResponseCode.TENCENT_CLOUD_EXCEPTION, errorMsg = e.message)
        } catch (e: Exception) {
            logger.error("腾讯云/人脸对比，传输图片URL:", e.printStackTrace())
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
}