package token

class OpeningBracket(
    override var token: Char,
    override var priority: Int
): Token {
}