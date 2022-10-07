import calculator.Calculator
import calculator.PostfixCalculator

fun main(args: Array<String>) {
    try {
        val calculator: Calculator = PostfixCalculator()
        println(calculator.calc(args[0]))
    } catch (e: Exception) {
        println(e.message)
        e.printStackTrace()
    }
}