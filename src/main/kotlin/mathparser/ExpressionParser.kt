package mathparser

import javax.swing.text.html.HTML.Tag.I
import kotlin.math.E
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.round

class ExpressionParser{
    private val numStack = Stack<Double>()
    private val opStack = Stack<String>()

    var isDegree = false
    private var isEnabled = false

    fun enableLog(status: Boolean){
        logEnabled = status
    }

    fun evaluate(expression : String, precision : Int = 3): Double{
        val uExpression = convertToUExpression(expression)
        val res = evaluateExpression(uExpression)
        return roundToPrecision(res, precision)
    }
    private fun convertToUExpression(expression: String): String{
        val sb = StringBuilder()
        for(i in expression.indices) {
            val currChar = expression[i]
            if (currChar.toString() == NormalOperators.MINUS.sign) {
                if (i == 0) {
                    sb.append('u')
                } else {
                    val prevChar = expression[i - 1]
                    if (prevChar in "+*/^E(") {
                        sb.append('u')
                    } else {
                        sb.append(currChar)
                    }
                }
            } else {
                sb.append(currChar)
            }
        }
        return sb.toString()
        }
    private fun roundToPrecision(value:Double, precision:Int = 3): Double{
        val corrector = 10.0.pow(precision).toInt()
        var result = round(value * corrector)/corrector
        if(result == -0.0){
            result = 0.0
        }
        return result
    }
    private fun computeNormamlOperation(op : String){
        try {
            when(op){
                NormalOperators.PLUS.sign -> {
                    val num0 = numStack.pop()
                    val num1 = numStack.pop()
                    numStack.push(num1 + num0)
                }
                NormalOperators.MINUS.sign -> {
                    val num0 = numStack.pop()
                    val num1 = numStack.pop()
                    numStack.push(num1 - num0)
                }
                NormalOperators.MULTIPLY.sign -> {
                    val num0 = numStack.pop()
                    val num1 = numStack.pop()
                    numStack.push(num1 *num0)
                }
                NormalOperators.DIVISION.sign -> {
                    val num0 = numStack.pop()
                    val num1 = numStack.pop()
                    numStack.push(num1 / num0)
                }
                NormalOperators.EXPONENTIAL.sign -> {
                    val num0 = numStack.pop()
                    val num1 = numStack.pop()
                    numStack.push(num1 * (10.0.pow(num0)))
                }
                NormalOperators.UNARY.sign -> {
                    val num0 = numStack.pop()
                    numStack.push(-1.0 * num0)
                }
            }
        }catch (es: IndexOutOfBoundsException){
            clearStacks()
            throw BadSyntaxException()
        }catch(ae: ArithmeticException) {
            clearStacks()
            throw Exception("Division by zero not possible")
        }
    }
    private fun evaluateExpression(expression: String) :Double{
        var i = 0;
        val numString = StringBuilder()
        while(i < expression.length) {
            val currChar = expression[i]
            if (currChar in "0123456789.") {
                if (i != 0 && (expression[i - 1] == ')' || expression[i - 1] == 'e' ||
                            (i >= 2 && expression.subString(i - 2, i)) == "PI")
                ) {
                    performSafePushToStack(numString, '*')
                }
                numString.append(currChar)
                i++
            } else if (currChar.toString() isIn NormalOperators.values() || currChar == '(') {
                if (currChar == '(') {
                    if (i != 0 && expression[i - 1].toString() notIn NormalOperators.values()) {
                        performSafePushToStack(numString, "*")
                    }
                    opStack.push("(")
                } else {
                    performSafePushToStack(numString, currChar.toString())
                }

                i++
            } else if (currChar == ')') {
                computeBracket(numString)
                i++
            } else if (currChar == '!') {
                performFactorial(numstring)
                i++
            } else if (currChar == '%') {
                performPercentage(numString)
                i++
            } else if (i + 2 <= expression.length && expression.substring(i, i + 2) == "PI") {
                // check for implicit multiply
                if (i != 0 && expression[i - 1].toString() notIn NormalOperators.values()
                    && expression[i - 1] != '('
                ) {
                    performSafePushToStack(numString, "*")
                }
                numStack.push(PI)
                i += 2
            } else if (expression[i] == 'e' &&
                (i + 1 == expression.length || (i + 1) < expression.length && expression[i + 1] != 'x')
            ) {
                //check for implicit multiply
                if (i != 0 && expression[i - 1].toString() notIn NormalOperators.values()
                    && expression[i - 1] != '('
                ) {
                    performSafePushToStack(numString, "*")
                }
                numStack.push(E)
                i++
            } else {
                //check for implicit MULTIPLY
                if (i != 0 && expression[i - 1].toString() notIn NormalOperators.values()
                    && expression[i - 1] != '('
                ) {
                    performSafePushToStack(numString, "*")
                }
                val increment = pushFunctionalOperator(expression, i)
                i += increment
            }
        }
        if(numString.isNotEmpty()){
            val number = numString.toString().toDouble()
            numStack.push(number)
            numString.clear()
        }
        while(!opStack.isEmpty()) {
            val op = opStack.pop()
            if (op isIn FunctionalOperators.values()) {
                clearStacks()
                throw BadSyntaxException()
            }
            computeNormamlOperation(op)
        }
        if(logEnabled) {
            opStack.display()
            numStack.display()
        }
        return try {
            numStack.pop()
        }catch (ie: IndexOutOfBoundsException) {
            clearStacks()
            throw BadSyntaxException()
        }
        private fun pushFunctionalOperator(
            expression:String,
            index: Int): Int{
            for(func in FunctionalOperators.values()){
                val funLength = func.func.length
                if((index + funLength < expression.length)&&
                        expression.substring(index, index + funLength) == func.func)
                {
                    if(func != FunctionalOperators.logx) {
                        opStack.push(func.func)
                        return funLength
                    }else{
                        val logregex = Regex("log[123456789.]+\\(")
                        val found = logregex.find(expression.substring(index, expression.length))
                        try{

                        }
                    }
                }



            }
            }
        }





)