package com.littlecorgi.courseji.common.utils

/**
 * String的工具类
 * @author littlecorgi
 * @date 2021/4/15
 */
fun String.isHttpOrHttps(): Boolean = this.startsWith("http://") or this.startsWith("https://")