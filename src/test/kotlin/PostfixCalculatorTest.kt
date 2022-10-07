import calculator.Calculator
import calculator.PostfixCalculator
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PostfixCalculatorTest {
    private val calculator: Calculator = PostfixCalculator()
    private val accuracy = 0.0000000001

    @Test
    internal fun testAddition() {
        val infix = "3.025+25.1"
        val expected = 28.125
        checkCalculation(infix, expected)
    }

    @Test
    internal fun testSubtraction() {
        val infix = "1.2-4"
        val expected = -2.8
        checkCalculation(infix, expected)
    }

    @Test
    internal fun testMultiplication() {
        val infix = "125.4*4.148"
        val expected = 520.1592
        checkCalculation(infix, expected)
    }

    @Test
    internal fun testDivision() {
        val infix = "305.5/5"
        val expected = 61.1
        checkCalculation(infix, expected)
    }

    @Test
    internal fun testExpressionWithBrackets() {
        val infix = "15.5/(7.2-(1+1))*3.121-(2+(1+1))*15.1/(7-(200.345+1))*3"
        val expected = 10.2353433204
        checkCalculation(infix, expected)
    }

    @Test
    internal fun assertDivisionByZeroThrowsException() {
        val infix = "15.5/(7.2-(1+1))/0"
        assertThrowsRuntimeException(infix, "Division by zero!")
    }

    @Test
    internal fun `when brackets are not balanced exception should be thrown`() {
        val moreClosing = "12.3*(7-(1+1)))/2"
        val moreOpening = "12.3*((7-(1+1))/2"
        assertThrowsRuntimeException(moreClosing, "Brackets are not balanced, the expression is invalid!")
        assertThrowsRuntimeException(moreOpening, "Brackets are not balanced, the expression is invalid!")
    }

    @Test
    internal fun `when unsupported token, unsupported op exception should be thrown`() {
        val infix = "1124.2+12*(5^45)"
        assertThrows<UnsupportedOperationException> { calculator.calc(infix) }
    }

    @Test
    internal fun `when invalid input expression, no such elem exception should be thrown`() {
        val infix = "5.2+-12*/"
        assertThrows<NoSuchElementException> { calculator.calc(infix) }
    }

    private fun checkCalculation(infix: String, expected: Double) {
        val actual = calculator.calc(infix)
        assert(expected >= actual - accuracy && expected <= actual + accuracy)
    }

    private fun assertThrowsRuntimeException(infix: String, msg: String) {
        assertThrows<RuntimeException>(msg) { calculator.calc(infix) }
    }
}