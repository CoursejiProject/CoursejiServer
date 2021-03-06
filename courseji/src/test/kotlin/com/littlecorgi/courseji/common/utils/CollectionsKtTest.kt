package com.littlecorgi.courseji.common.utils

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * [com.littlecorgi.courseji.common.utils.CollectionsKtTest] 的单元测试
 * @author littlecorgi
 * @date 2021/4/18
 */
internal class CollectionsKtTest {

    @BeforeEach
    fun setUp() {
    }

    @Test
    fun testTwoArrayIntersect() {
        var aStart = 1
        var aStep = 3
        var bStart = 2
        var bStep = 3
        // [1, 2, 3] [2, 3, 4]
        assertTrue(twoArrayIntersect(aStart, aStep, bStart, bStep))
        aStart = 2
        aStep = 3
        bStart = 7
        bStep = 5
        assertFalse(twoArrayIntersect(aStart, aStep, bStart, bStep))

        var isLeave = false
        var leaveTime = 1..2
        var attendanceTime = 3..4
        if (leaveTime.intersect(attendanceTime).isNotEmpty()) {
            isLeave = true
        }
        assertFalse(isLeave)

        leaveTime = 1..5
        attendanceTime = 5..6
        if (leaveTime.intersect(attendanceTime).isNotEmpty()) {
            isLeave = true
        }
        assertTrue(isLeave)
    }
}
