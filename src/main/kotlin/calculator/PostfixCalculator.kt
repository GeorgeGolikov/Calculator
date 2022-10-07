package calculator

import expression.impl.PostfixExpression
import parser.OperatorPrecedenceParser
import parser.impl.ShuntingYardParser
import token.operation.BinaryOperation
import token.operation.UnaryOperation

class PostfixCalculator: Calculator {
    private var parser: OperatorPrecedenceParser = ShuntingYardParser()
    private val stack = ArrayDeque<Double>()

    override fun calc(infix: String): Double {
        try {
            val postfix = parser.parse(infix)
            return calculateFromPostfixWithStack(postfix)
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException("ArrayDeque got empty while calculating. The input expression is invalid")
        }
    }

    fun setParser(parser: OperatorPrecedenceParser) {
        this.parser = parser
    }

    private fun calculateFromPostfixWithStack(postfix: PostfixExpression): Double {
        var i = 0
        var numberOrToken = postfix.get(i)
        while (numberOrToken != null) {
            when(numberOrToken) {
                is Double -> {
                    addCurrentNumToStack(numberOrToken)
                }
                is BinaryOperation -> {
                    processBinaryOp(numberOrToken)
                }
                is UnaryOperation -> {
                    processUnaryOp(numberOrToken)
                }
            }
            ++i
            numberOrToken = postfix.get(i)
        }
        return stack.removeFirst()
    }

    private fun addCurrentNumToStack(num: Double) {
        stack.addFirst(num)
    }

    private fun processBinaryOp(operation: BinaryOperation) {
        val rightOperand = stack.removeFirst()
        val leftOperand = stack.removeFirst()
        val opRes = operation.apply(leftOperand, rightOperand)
        addCurrentNumToStack(opRes)
    }

    private fun processUnaryOp(operation: UnaryOperation) {
        val lastOperand = stack.removeFirst()
        val opRes = operation.apply(lastOperand)
        addCurrentNumToStack(opRes)
    }
}