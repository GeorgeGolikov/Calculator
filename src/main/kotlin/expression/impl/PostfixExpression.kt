package expression.impl

import expression.Expression
import token.operation.Operation

class PostfixExpression: Expression {
    private val numbers = ArrayList<Double?>()
    private val operations = ArrayList<Operation?>()

    fun add(number: Double) {
        numbers.add(number)
        operations.add(null)
    }

    fun add(operation: Operation) {
        operations.add(operation)
        numbers.add(null)
    }

    fun get(index: Int): Any? {
        if (index >= numbers.size || index < 0) return null
        return numbers[index] ?: operations[index]
    }
}