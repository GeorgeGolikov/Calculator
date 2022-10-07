package token.operation

class SubtractOperation(
    override var token: Char,
    override var priority: Int
): BinaryOperation {
    override fun apply(p0: Double, p1: Double): Double {
        return p0 - p1
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SubtractOperation

        if (token != other.token) return false

        return true
    }

    override fun hashCode(): Int {
        return token.hashCode()
    }


}