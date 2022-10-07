import expression.impl.PostfixExpression
import org.junit.jupiter.api.Test
import parser.impl.ShuntingYardParser
import token.TokenHandler
import token.operation.Operation
import kotlin.test.assertEquals
import kotlin.test.fail

class ShuntingYardParserTest {
    private val parser = ShuntingYardParser()
    private val tokenHandler = TokenHandler()

    @Test
    internal fun testToPostfix() {
        val infix = "15/(7-(1+1))"
        val expectedPostfix = PostfixExpression()
        expectedPostfix.add(15.0)
        expectedPostfix.add(7.0)
        expectedPostfix.add(1.0)
        expectedPostfix.add(1.0)
        expectedPostfix.add(tokenHandler.getTokenObjFromChar('+') as Operation)
        expectedPostfix.add(tokenHandler.getTokenObjFromChar('-') as Operation)
        expectedPostfix.add(tokenHandler.getTokenObjFromChar('/') as Operation)

        val actualPostfix = parser.parse(infix)
        var i = 0
        var numberOrTokenAct = actualPostfix.get(i)
        var numberOrTokenExp = expectedPostfix.get(i)
        while (numberOrTokenAct != null && numberOrTokenExp != null) {
            assertEquals(numberOrTokenExp, numberOrTokenAct)
            ++i
            numberOrTokenAct = actualPostfix.get(i)
            numberOrTokenExp = expectedPostfix.get(i)
        }
        if (numberOrTokenAct != null || numberOrTokenExp != null) {
            fail("Actual and expected expressions have different lengths")
        }
    }
}