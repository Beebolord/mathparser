package mathparser
import kotlin.math.*
class AlgebraicParser {
    fun quadraticFormula(alpha: Double, beta: Double, zeta: Double): String {

        val subQuadFormula = subQuadFormula(alpha, beta, zeta)
        val exesFirst = (((-beta) + (sqrt(subQuadFormula))) / (2.00 * alpha))
        val exesSecond = (((-beta) - (sqrt(subQuadFormula))) / (2.00 * alpha))

        println("$subQuadFormula")

        return if (subQuadFormula > 0) {
           exesFirst.toString() + "this is too long" + exesSecond.toString()
        } else {
            throw IllegalArgumentException("errors were made heres's the proof: $subQuadFormula")
        }
    }
    private fun subQuadFormula(alpha: Double, beta: Double, zeta: Double): Double {
        return (((beta.pow(2)) + (-4 * (alpha * zeta))))
    }
}
