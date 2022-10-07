package parser.impl

import expression.impl.PostfixExpression
import token.TokenHandler
import parser.OperatorPrecedenceParser
import token.OpeningBracket
import token.Token
import token.operation.Operation

class ShuntingYardParser: OperatorPrecedenceParser {
    private var tokenHandler = TokenHandler()
    private val currentPostfixExpression = PostfixExpression()
    private var currentExpressionIndex = 0
    private val stack = ArrayDeque<Token>()

    override fun parse(expression: String): PostfixExpression {
        addCharsToStackAndPostfix(expression)
        addLeftInStackToPostfix()
        return currentPostfixExpression
    }

    private fun addCharsToStackAndPostfix(expression: String) {
        while (currentExpressionIndex < expression.length) {
            val char = expression[currentExpressionIndex]
            addToStackOrPostfixDependingOnType(char, expression)
            ++currentExpressionIndex
        }
    }

    private fun addToStackOrPostfixDependingOnType(char: Char, expression: String) {
        if (Character.isDigit(char)) {
            addNumToPostfix(expression)

        } else if (isOpeningBracket(char)) {
            addOpeningBracketToStack(char)

        } else if (isClosingBracket(char)) {
            addFromStackToPostfixUntilOpenBracket()

        } else {
            addOperationToStackAndOtherToPostfix(char)
        }
    }

    private fun addNumToPostfix(expression: String) {
        val num = getNumFromString(expression)
        currentPostfixExpression.add(num)
    }

    private fun isOpeningBracket(char: Char): Boolean {
        return char == '('
    }

    private fun addOpeningBracketToStack(char: Char) {
        val openingBracket = tokenHandler.getTokenObjFromChar(char)
        stack.addFirst(openingBracket)
    }

    private fun isClosingBracket(char: Char): Boolean {
        return char == ')'
    }

    private fun addFromStackToPostfixUntilOpenBracket() {
        while (stack.isNotEmpty() && stack.first() !is OpeningBracket) {
            currentPostfixExpression.add(stack.removeFirst() as Operation)
        }
        if (stack.isEmpty()) {
            throw RuntimeException("Brackets are not balanced, the expression is invalid!")
        }
        stack.removeFirst()
    }

    private fun addOperationToStackAndOtherToPostfix(char: Char) {
        val token = tokenHandler.getTokenObjFromChar(char)
        addFromStackToPostfixWhileHigherPriority(token)
        stack.addFirst(token)
    }

    private fun addFromStackToPostfixWhileHigherPriority(token: Token) {
        while (stack.isNotEmpty() && stack.first().priority >= token.priority) {
            currentPostfixExpression.add(stack.removeFirst() as Operation)
        }
    }

    private fun getNumFromString(fromStr: String): Double {
        val sb = StringBuilder("")
        var dotCounter = 0
        while (currentExpressionIndex < fromStr.length) {
            val char = fromStr[currentExpressionIndex]
            if (isDigitOrFirstDot(char, dotCounter)) {
                if (isDot(char)) ++dotCounter
                sb.append(char)
                ++currentExpressionIndex
            } else {
                --currentExpressionIndex
                break
            }
        }
        return sb.toString().toDouble()
    }

    private fun isDigitOrFirstDot(char: Char, dotCounter: Int): Boolean {
        return Character.isDigit(char) || (char == '.' && dotCounter == 0)
    }

    private fun isDot(char: Char): Boolean {
        return char == '.'
    }

    private fun addLeftInStackToPostfix() {
        while (stack.isNotEmpty()) {
            currentPostfixExpression.add(stack.removeFirst() as Operation)
        }
    }
}