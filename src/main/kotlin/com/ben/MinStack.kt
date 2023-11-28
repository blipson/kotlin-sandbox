package com.ben

data class Node(
    var `val`: Int,
    val min: Int,
    val next: Node? = null
)

class MinStack() {
    private var top: Node? = null

    fun push(`val`: Int) {
        val nodeToPush = Node(
            `val`, top?.min?.let { if (`val` < it) `val` else it } ?: `val`, top
        )
        top = nodeToPush
    }

    fun pop() {
        top = top?.next
    }

    fun top(): Int {
        return top?.`val` ?: throw Exception("Stack is empty")
    }

    fun getMin(): Int {
        return top?.min ?: throw java.lang.Exception("Stack is empty")
    }
}
