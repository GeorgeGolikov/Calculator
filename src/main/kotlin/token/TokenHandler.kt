package token

import token.operation.AddOperation
import token.operation.DivideOperation
import token.operation.MultiplyOperation
import token.operation.SubtractOperation

open class TokenHandler {
    protected val operations = HashMap<Char, Token>()

    init {
        operations['('] = OpeningBracket('(', 0)
        operations['+'] = AddOperation('+', 1)
        operations['-'] = SubtractOperation('-', 1)
        operations['*'] = MultiplyOperation('*', 2)
        operations['/'] = DivideOperation('/', 2)
    }

    fun getTokenObjFromChar(c: Char): Token {
        return operations.getOrElse(c) {
            throw UnsupportedOperationException()
        }
    }
}