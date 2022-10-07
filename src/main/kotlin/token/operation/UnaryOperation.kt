package token.operation

import java.util.function.UnaryOperator

interface UnaryOperation: Operation, UnaryOperator<Double> {
}