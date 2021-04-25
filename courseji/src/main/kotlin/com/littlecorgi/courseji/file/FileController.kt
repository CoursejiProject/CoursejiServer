package com.littlecorgi.courseji.file

import com.littlecorgi.courseji.common.ResponseCode
import com.littlecorgi.courseji.common.ServerResponse
import com.qiniu.common.QiniuException
import com.qiniu.http.Response
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.FileReader

/**
 * 文件操作相关controller
 *
 * @author littlecorgi
 * @date 2021/4/23
 */
@Api
@RestController
@RequestMapping(path = ["/file"])
class FileController {

    @Autowired
    private lateinit var fileService: FileService

    @ApiOperation(value = "获取日志文件（不可用，请使用/actuator/logfile接口）")
    @GetMapping(path = ["/coursejiLog"])
    fun getLogger(): ServerResponse<String> =
        try {
            val br = BufferedReader(FileReader("~/CoursejiServer/courseji.txt"))
            val sb = StringBuffer()
            var str: String?
            while (br.readLine().also { str = it } != null) sb.append(str)
            ServerResponse.createBySuccess(sb.toString())
        } catch (e: Exception) {
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }

    @ApiOperation(value = "上传图片文件")
    @PostMapping(path = ["/uploadPicture"])
    fun uploadPicture(
        @ApiParam(value = "需要上传的图片文件", required = true) @RequestBody picFile: MultipartFile
    ): ServerResponse<String> =
        try {
            ServerResponse.createBySuccess(fileService.uploadPicture(picFile))
        } catch (ex: QiniuException) {
            val r: Response = ex.response
            try {
                ServerResponse.createByFailure(ResponseCode.QINIU_FAILURE, errorMsg = r.bodyString())
            } catch (ex2: QiniuException) {
                ServerResponse.createByFailure(ResponseCode.QINIU_FAILURE, errorMsg = ex2.message ?: "error")
            }
        } catch (e: Exception) {
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
}