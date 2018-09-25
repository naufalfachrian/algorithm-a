package id.bungamungil.hellospring.module

import org.nield.kotlinstatistics.median
import org.nield.kotlinstatistics.standardDeviation
import kotlin.math.absoluteValue
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sqrt

object AlgA {

    fun calculate(input: List<Double>): AlgA.Result {
        var numbers = input
        var sd: Double
        val x = numbers.median()

        val initialComparator = numbers.map { (it - x).absoluteValue }

        val medOfInitial = initialComparator.median()
        var s = 1.483 * medOfInitial

        numbers = numbers.map { number ->
            operate(number, x, s)
        }

        var comparator = calculateComparator(numbers)
        var sumA = comparator.sum()
        var sumB = sumA / (comparator.count() - 1)
        var sumC = sqrt(sumB)
        var i = 0

        var av: Double
        do {
            sd = numbers.standardDeviation()
            av = numbers.average()

            s = 1.134 * sumC

            numbers = numbers.map { number ->
                operate(number, av, s)
            }

            comparator = calculateComparator(numbers)
            sumA = comparator.sum()
            sumB = sumA / (comparator.count() - 1)
            sumC = sqrt(sumB)

            i += 1

        } while (!validated(av, sd, s, numbers.average(), numbers.standardDeviation(), 1.134 * sumC))

        numbers.forEach {
            println(String.format("%.2f", it))
        }

        val algA = numbers.average()
        return Result(i, algA, 1.134 * numbers.standardDeviation(), calculateSHorwit(algA))
    }

    private fun operate(number: Double, _x: Double, s: Double): Double {
        val delta = 1.5 * s
        val deltaA = _x - delta
        val deltaB = _x + delta
        if (number < deltaA) {
            return deltaA
        }
        if (number > deltaB) {
            return deltaB
        }
        return number
    }

    private fun validated(av: Double, sd: Double, _s: Double, _av: Double, _sd: Double, __s: Double): Boolean {
        val formatter = "%.2f"
        val a = String.format(formatter, av) == String.format(formatter, _av)
        val b = String.format(formatter, sd) == String.format(formatter, _sd)
        val c = String.format(formatter, _s) == String.format(formatter, __s)
        return a && b && c
    }

    private fun calculateSHorwit(algA: Double): Double {
        val logC = log10(algA / 1_000_000)
        val kvHorwit = (2.0).pow(1 - (0.5 * logC))
        return algA * kvHorwit / 100
    }

    private fun calculateComparator(numbers: List<Double>) : List<Double> {
        val average = numbers.average()
        return numbers.map { (it - average).pow(2) }
    }


    data class Result(
            val iteration: Int,
            val mean: Double,
            val standardDeviation: Double,
            val sHorwit: Double
    )

}