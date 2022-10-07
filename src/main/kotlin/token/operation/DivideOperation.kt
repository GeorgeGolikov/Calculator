package token.operation

import kotlin.math.abs

class DivideOperation(
    override var token: Char,
    override var priority: Int
): BinaryOperation {
    private val accuracy = 0.000000000001
    override fun apply(p0: Double, p1: Double): Double {
        if (abs(p1) < accuracy) {
            throw RuntimeException("Division by zero!")
        }
        return p0 / p1
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DivideOperation

        if (token != other.token) return false

        return true
    }

    override fun hashCode(): Int {
        return token.hashCode()
    }


}