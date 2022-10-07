package token.operation

import java.util.function.BinaryOperator

interface BinaryOperation: Operation, BinaryOperator<Double> {
}