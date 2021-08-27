import com.sun.org.apache.xalan.internal.lib.ExsltDynamic.evaluate
import sun.text.normalizer.UTF16.append
import java.util.*
import javax.management.Query.value
import kotlin.math.*
import com.sun.tools.example.debug.expr.ExpressionParser as ExpressionParser1


private enum class Operators(val sign: Char) {
    MINUS('-'),
    PLUS('+'),
    MULTIPLY('*'),
    DIVISION('/'),
    POWER('^'),
    EXPONENTIAL('E');
}
private fun String.split(position :Int)=
    listOf(
        this.substring(0, position),
        this.substring(position + 1, this.length),
    )
private fun extractNumber(numString : String) = numString.toDoubleOrNull()
private fun isValue(expression: String) : Boolean{
    val validChar = "1234567890.-"
    for(i in expression.indices){
        val char = expression[i]
        if(char !in validChar) return false
        if(expression.count {it == '.'} > 1) return false
        if(char == '-' && i != 0) return false
    }
    return true
}
private fun String.lastIndex(char:Char): Int{
    var bOpen  = 0
    var bClose = 0
    for(i in this.indices) {
        val currChar = this[i]
        when {
            currChar == char && bOpen == bClose -> return this.length - i - 1
            currChar == '(' -> bOpen++
            currChar == ')' -> bClose++
        }
    }
    return -1
}
private fun isOperator(operator :Operators, expression :String, position :Int):Boolean {
    if (operator == Operators.PLUS) {
        if (expression[position - 1] == 'E') {
            if (position >= 2)
                return false
        } else {
            return true
        }
    } else if(operator == Operators.MINUS){
        if(position == 0) {
            return false
        }else if(expression[position-1] == 'E' && position >= 2) {
            return false
        }else{
            val prevOp = expression[position -1]
            for(legalOp in Operators.values()) {
                if (prevOp == legalOp.sign) {
                    return false
                }
                println("returning the minus operator")
                return true
            }
            }
        return true

        }
}
private fun evaluateFunction(funString: String, value: Double): Double {
    return when (funString) {
        // Trigonometric
        "SIN", "sin", "Sin" -> sin(value)
        "COS", "cos", "Cos" -> cos(value)
        "TAN", "tan", "Tan" -> tan(value)
        "ASIN", "asin" -> asin(value)
        "ACOS", "acos" -> acos(value)
        "ATAN", "atan" -> atan(value)

        //arithmetic
        "LOG10", "log10", "Log10" -> log10(value)
        "LN", "Ln", "ln" -> ln(value)
        "SQRT", "sqrt", "Sqrt" -> sqrt(value)
        "EXP", "exp", "Exp" -> exp(value)

        //hyperbolic
        "SINH", "sinh", "Sinh" -> sinh(value)
        "COSH", "cosh", "Cosh" -> cosh(value)
        "TANH", "tanh", "Tanh" -> tanh(value)


        else -> throw
        ArithmeticException("Function cannot be determined $funString")
    }
}
private fun roundToPrecision(value: Double, precision: Int = 3,):Double{
    val corrector =  10.0.pow(precision).toInt()
    return round(value * corrector) / corrector
}
fun evaluateFunction(expression : String, precision: Int = 3): Double{
    val res = evaluate(expression)
    return (roundToPrecision(res, precision))

}
private fun evaluate(expression:String):Double{
    for(operator in Operators.values()){
        var position = position.reversed.lastIndexOf(operator.sign)
    }
}
fun main(args: Array<String>) {
    println("Hello World!")
    val result = ExpressionParser1().evaluate("sin(PI)+1+cos(PI)")
    val result = parser.evaluate("sin(PI)+1+cos(PI)")

    // Try adding program arguments at Run/Debug configuration
    println("Give me a french words:")
    val read = readLine()
}