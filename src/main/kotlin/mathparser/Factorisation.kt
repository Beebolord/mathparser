package mathparser

class Factorisation {
    fun simplyFactorised (equation: String): Any
    {
        var i = 0;
        val polynomial = listOf<String>

        while(i < equation.length - 1){
            var currChar = equation[i]

            polynomial += equation.split("+","-")[i]
            i++
        }
        println("this is something ${polynomial[0]} init")
        return true
    }
}