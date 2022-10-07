package parser

import expression.Expression

interface ArithmeticParser {
    fun parse(expression: String): Expression
}