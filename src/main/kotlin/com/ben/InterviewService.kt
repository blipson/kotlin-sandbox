package com.ben

import jakarta.inject.Singleton
import java.util.*


class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

@Singleton
class InterviewService {
    val votes = mutableMapOf<String, Int>()
    fun vote(candidate: String) {
        if (votes[candidate] == null) {
            votes[candidate] = 1
        } else {
            votes[candidate] = votes[candidate]!! + 1
        }
    }

    private fun canFinishCourse(course: Int, courseMap: Map<Int, List<Int>>, visitSet: Set<Int> = setOf()): Boolean {
        val deps = courseMap[course]
        return if (visitSet.contains(course)) {
            false
        } else if (deps.isNullOrEmpty()) {
            true
        } else {
            for (dep in deps) {
                val canFinishCourse = canFinishCourse(dep, courseMap)
                if (!canFinishCourse) {
                    return false
                }
            }
            return true
        }
    }


    fun canFinish(numCourses: Int, prerequisites: Array<IntArray>): Boolean {
        val courseMap: Map<Int, List<Int>> = prerequisites.associate { prereq ->
            prereq.first() to prerequisites.filter { innerPrereq ->
                prereq.first() == innerPrereq.first()
            }.map { it.last() }
        }
        for (i: Int in 0..numCourses) {
            val canFinishCourse = canFinishCourse(i, courseMap)
            if (!canFinishCourse) {
                return false
            }
        }
        return true
    }

    private val sorted = mutableListOf<Int>()

    private fun sortNodes(node: TreeNode) {
        if (!sorted.contains(node.`val`)) {
            if (sorted.isEmpty()) {
                sorted.add(node.`val`)
            } else {
                if (node.`val` >= sorted.last()) {
                    sorted.add(node.`val`)
                } else {
                    for (i: Int in (0..sorted.size)) {
                        if (node.`val` >= sorted[i] && node.`val` < sorted[i + 1]) {
                            sorted.add(i + 1, node.`val`)
                            break
                        } else {
                            sorted.add(i, node.`val`)
                            break
                        }
                    }
                }
            }
        }

        val left = node.left
        val right = node.right
        if (left == null && right == null) {
            return
        } else {
            if (left != null) {
                sortNodes(left)
            }
            if (right != null) {
                sortNodes(right)
            }
        }
    }

    fun kthSmallest(root: TreeNode, k: Int): Int {
        sortNodes(root)
        return sorted[k - 1]
    }


    fun reductionOperations(nums: IntArray): Int {
        Arrays.sort(nums)
        var ans = 0
        var up = 0
        for (i in 1 until nums.size) {
            if (nums[i] != nums[i - 1]) {
                up++
            }
            ans += up
        }
        return ans
    }

    private fun getMinutes(garbage: Array<String>, travel: IntArray, type: Char): Int {
        var minutes = 0
        var lastHouseWithGarbage = 0
        for (i: Int in (garbage.indices)) {
            val house = garbage[i]
            val minutesToPickUpAtHouse = house.count { c -> c == type }
            if (minutesToPickUpAtHouse > 0) {
                lastHouseWithGarbage = i
            }
            minutes += minutesToPickUpAtHouse
        }
        return if (lastHouseWithGarbage > 0) {
            minutes + travel.slice(0..<lastHouseWithGarbage).sum()
        } else {
            minutes
        }
    }

    fun garbageCollection(garbage: Array<String>, travel: IntArray): Int {
        val paperMinutes: Int = getMinutes(garbage, travel, 'P')
        val metalMinutes: Int = getMinutes(garbage, travel, 'M')
        val glassMinutes: Int = getMinutes(garbage, travel, 'G')
        return paperMinutes + metalMinutes + glassMinutes
    }


    private fun reverseNumber(n: Int): Int {
        var num = n
        var reversed = 0
        while (num != 0) {
            val digit = num % 10
            reversed = reversed * 10 + digit
            num /= 10
        }
        return reversed
    }

    private fun getValToAdd(n: Int): Int {
        var sum = 0
        var curr = n
        while (curr > 1) {
            sum += curr - 1
            curr--
        }
        return sum
    }

    fun countNicePairs(nums: IntArray): Int {
        val numsRes = nums.map { n ->
            n - reverseNumber(n)
        }
        val numsResFreq2: Map<Int, Int> = numsRes.groupingBy { it }.eachCount()
        val mod = 1e9.toInt() + 7
        return numsResFreq2.filter { it.value > 1 }.map { getValToAdd(it.value) }.sum() % mod
    }

    fun findDiagonalOrder(nums: List<List<Int>>): IntArray {
        val diagonalRowMap = mutableMapOf<Int, List<Int>>()
        for (rowIdx: Int in (nums.indices)) {
            for (colIdx: Int in (nums[rowIdx].indices)) {
                if (diagonalRowMap[rowIdx + colIdx] == null) {
                    diagonalRowMap[rowIdx + colIdx] = listOf(nums[rowIdx][colIdx])
                } else {
                    diagonalRowMap[rowIdx + colIdx] = listOf(nums[rowIdx][colIdx]) + diagonalRowMap[rowIdx + colIdx]!!
                }
            }
        }
        return diagonalRowMap.flatMap { it.value }.toIntArray()
    }

    fun maxCoins(piles: IntArray): Int {
        return piles.sorted().slice((piles.size / 3..<piles.size)).filterIndexed { i, _ -> i % 2 == 0 }.sum()
    }

    private var memo: Array<IntArray> = arrayOf()
    private val mod = 1e9.toInt() + 7
    private val possibleMoves = mapOf(
        0 to listOf(4, 6),
        1 to listOf(6, 8),
        2 to listOf(7, 9),
        3 to listOf(4, 8),
        4 to listOf(3, 9, 0),
        5 to listOf(),
        6 to listOf(1, 7, 0),
        7 to listOf(2, 6),
        8 to listOf(1, 3),
        9 to listOf(2, 4)
    )

    private fun getLines(remainingMoves: Int, currentSquare: Int): Int {
        if (remainingMoves == 0) {
            return 1
        }
        if (memo[remainingMoves][currentSquare] != 0) {
            return memo[remainingMoves][currentSquare]
        }
        var ret = 0
        for (nextSquare in possibleMoves.getOrElse(currentSquare) { listOf() }) {
            ret = (ret + getLines(remainingMoves - 1, nextSquare)) % mod
        }
        memo[remainingMoves][currentSquare] = ret
        return ret
    }

    fun knightDialer(n: Int): Int {
        memo = Array(n + 1) { IntArray(10) }
        var ans = 0
        for (square in 0..9) {
            ans = (ans + getLines(n - 1, square)) % mod
        }
        return ans
    }
}
