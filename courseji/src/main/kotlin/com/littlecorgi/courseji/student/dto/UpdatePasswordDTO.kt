package com.littlecorgi.courseji.student.dto

/**
 *
 * @author littlecorgi
 * @date 2021/4/26
 */
data class UpdatePasswordDTO(
    val oldPassword: String,
    val newPassword: String
)
