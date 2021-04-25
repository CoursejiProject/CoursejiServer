package com.littlecorgi.courseji.file

import com.littlecorgi.courseji.common.utils.QiniuUtils
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

/**
 *
 * @author littlecorgi
 * @date 2021/4/25
 */
@Service
class FileServiceImpl : FileService {

    override fun uploadPicture(picFile: MultipartFile): String =
        QiniuUtils.uploadPic(picFile)
}
