package parser

import expression.impl.PostfixExpression

interface OperatorPrecedenceParser: ArithmeticParser {
    override fun parse(expression: String): PostfixExpression
}