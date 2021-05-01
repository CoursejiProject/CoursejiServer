package com.littlecorgi.courseji.common

import com.littlecorgi.courseji.common.constants.ResponseCodeConstants.ATTENDANCE_ERROR_CODE
import com.littlecorgi.courseji.common.constants.ResponseCodeConstants.COURSE_ERROR_CODE
import com.littlecorgi.courseji.common.constants.ResponseCodeConstants.FAILURE_CODE
import com.littlecorgi.courseji.common.constants.ResponseCodeConstants.LEAVE_ERROR_CODE
import com.littlecorgi.courseji.common.constants.ResponseCodeConstants.SCHEDULE_ERROR_CODE
import com.littlecorgi.courseji.common.constants.ResponseCodeConstants.SUCCESS_CODE
import com.littlecorgi.courseji.common.constants.ResponseCodeConstants.USER_ERROR_CODE

/**
 * 响应状态码
 *
 * @author littlecorgi
 * @date 2021/4/14
 */
enum class ResponseCode(val code: Int, val msg: String) {

    // 成功
    SUCCESS(SUCCESS_CODE, "成功."),

    // 失败
    FAILURE(FAILURE_CODE, "失败!"),

    // 七牛云错误
    QINIU_FAILURE(FAILURE_CODE + 1, "七牛云错误!"),

    // 文件是空的
    FILE_IS_EMPTY(FAILURE_CODE + 2, "文件是空的!"),

    TENCENT_CLOUD_EXCEPTION(FAILURE_CODE + 3, "腾讯云错误信息"),

    /***********************
     * 用户相关
     **********************/

    // 密码错误
    PASSWORD_ERROR(USER_ERROR_CODE, "用户密码错误!"),

    // 用户已经存在
    USER_HAS_EXIST(USER_ERROR_CODE + 1, "用户已经存在!"),

    // 该用户不存在
    NO_USER(USER_ERROR_CODE + 2, "用户不存在!"),

    // 用户信息存在问题
    USER_INFO_INVALID(USER_ERROR_CODE + 3, "用户信息错误!"),

    /***********************
     * 课程详情相关
     **********************/

    // 课程已经存在
    COURSE_HAS_EXIST(COURSE_ERROR_CODE, "课程已经存在!"),

    // 课程不存在
    NO_COURSE(COURSE_ERROR_CODE + 1, "课程不存在!"),

    // 课程信息存在问题
    COURSE_INFO_INVALID(COURSE_ERROR_CODE + 2, "课程信息错误!"),

    // 学生已经加入课程
    STUDENT_HAS_JOINED_COURSE(COURSE_ERROR_CODE + 3, "该学生已经加入此课程!"),

    /***********************
     * 学生课程对应关系相关
     **********************/

    // 学生上课对应关系不存在
    NO_SCHEDULE(SCHEDULE_ERROR_CODE, "学生上课对应关系不存在!"),

    /***********************
     * 考勤相关
     **********************/

    // 考勤信息已经存在
    ATTENDANCE_HAS_EXIST(ATTENDANCE_ERROR_CODE, "考勤信息已经存在!"),

    // 考勤信息不存在
    NO_ATTENDANCE(ATTENDANCE_ERROR_CODE + 1, "考勤信息不存在!"),

    // 考勤信息存在问题
    ATTENDANCE_INFO_INVALID(ATTENDANCE_ERROR_CODE + 2, "考勤信息错误!"),

    /**********************
     * 请假相关
     **********************/

    // 课程已经存在
    LEAVE_HAS_EXIST(LEAVE_ERROR_CODE, "请假已经存在!"),

    // 请假不存在
    NO_LEAVE(LEAVE_ERROR_CODE + 1, "请假不存在！"),

    // 请假信息存在问题
    LEAVE_INFO_INVALID(LEAVE_ERROR_CODE + 2, "请假信息错误!"),
}
