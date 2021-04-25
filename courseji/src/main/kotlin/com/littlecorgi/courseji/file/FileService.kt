package com.littlecorgi.courseji.file

import com.qiniu.common.QiniuException
import org.springframework.web.multipart.MultipartFile

/**
 *
 * @author littlecorgi
 * @date 2021/4/25
 */
interface FileService {

    /**
     * 上传图片，返回url
     *
     * @param picFile 需要上传的文件
     * @return 上传后的url
     * @throws QiniuException 七牛云SDK的错误
     */
    fun uploadPicture(picFile: MultipartFile): String
}