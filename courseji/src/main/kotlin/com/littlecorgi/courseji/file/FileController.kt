package com.littlecorgi.courseji.file

import com.littlecorgi.courseji.common.ResponseCode
import com.littlecorgi.courseji.common.ServerResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.BufferedReader
import java.io.FileReader

/**
 * 文件操作相关controller
 *
 * @author littlecorgi
 * @date 2021/4/23
 */
@RestController
@RequestMapping(path = ["/file"])
class FileController {

    @GetMapping(path = ["/coursejiLog"])
    fun getLogger(): ServerResponse<String> =
        try {
            val br = BufferedReader(FileReader("~/CoursejiServer/courseji.txt"))
            val sb = StringBuffer()
            var str: String? = null
            while (br.readLine().also { str = it } != null) sb.append(str)
            ServerResponse.createBySuccess(sb.toString())
        } catch (e: Exception) {
            ServerResponse.createByFailure(ResponseCode.FAILURE, errorMsg = e.message)
        }
}