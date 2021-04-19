package com.littlecorgi.courseji.common.utils

/**
 * 集合相关扩展类
 *
 * @author littlecorgi
 * @date 2021/4/18
 */
/**
 * 两个集合是否有交集。
 * 集合都是从start开始，差为1的等差递增数组，数组长度为step。
 * 例如：start=3，step=5，集合为[3, 4, 5, 6, 7]
 *
 * @param aStart A集合的起始值
 * @param aStep A集合的长度
 * @param bStart B集合的起始值
 * @param bStep B集合的长度
 * @return A集合和B集合是否相交
 */
fun twoArrayIntersect(aStart: Int, aStep: Int, bStart: Int, bStep: Int): Boolean {
    val aArray = ArrayList<Int>()
    val bArray = ArrayList<Int>()
    var i = aStart
    repeat(aStep) {
        aArray.add(i)
        i++
    }
    i = bStart
    repeat(bStep) {
        bArray.add(i)
        i++
    }
    return aArray.intersect(bArray).isNotEmpty()
}
