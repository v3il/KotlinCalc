package com.kit.kotlincalc

import java.math.BigDecimal

class ExpressionPart(val expressionPartToEvaluate: String) {

    fun evaluatePart(): String {
        val parts = expressionPartToEvaluate
                .split(" ")
                .filter { it.trim().length > 0 }

        val first: Double = stringToDouble(parts[0])
        val sign = parts[1]
        val second: Double = stringToDouble(parts[2])

        val result = when(sign) {
            "*" -> first * second
            "/" -> first / second
            "+" -> first + second
            "-" -> first - second
            else -> first * second
        }

        if(Launcher.Settings.printIntermediateResults) {
            println("$first $sign $second = $result")
        }

        return BigDecimal
                .valueOf(result)
                .setScale(Launcher.Settings.resultPrecision, BigDecimal.ROUND_HALF_UP)
                .toString()
    }

    fun stringToDouble(numberRepresentation: String): Double {
        return try {
            numberRepresentation.toDouble()
        } catch (e: NumberFormatException) {
            0.0
        }
    }

}