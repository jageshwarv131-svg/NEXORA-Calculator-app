package com.example.engine

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.*

object MathEvaluator {

    /**
     * Evaluates a mathematical expression string.
     * Supports:
     * - standard ops: +, -, *, /, ×, ÷, %
     * - brackets: (, )
     * - constants: π, e
     * - functions: sin, cos, tan, sinh, cosh, tanh, lg, √, 1/x
     * - isRad: true for radians, false for degrees
     */
    fun evaluate(expression: String, isRad: Boolean = true): String {
        try {
            var sanitized = expression
                .replace("×", "*")
                .replace("÷", "/")
                .replace(" ", "")
                .trim()

            if (sanitized.isEmpty()) return "0"

            val parser = Parser(sanitized, isRad)
            val result = parser.parse()

            if (!result.isFinite() || result.isNaN()) {
                return "Error"
            }

            return formatResult(result)
        } catch (_: Exception) {
            return "Error"
        }
    }

    private fun formatResult(value: Double): String {
        if (value == 0.0 || value == -0.0) return "0"

        // Handle integer values
        if (abs(value) < 1e12 && abs(value) >= 1e-6) {
            val bd = BigDecimal(value.toString())
                .setScale(8, RoundingMode.HALF_UP)
                .stripTrailingZeros()
            return bd.toPlainString()
        }

        // Extremely large or small: use scientific or trimmed decimal
        val bd = BigDecimal(value.toString())
        return if (bd.scale() > 8) {
            bd.setScale(8, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString()
        } else {
            bd.stripTrailingZeros().toPlainString()
        }
    }

    private class Parser(val input: String, val isRad: Boolean) {
        var pos = -1
        var ch = 0

        fun nextChar() {
            pos++
            ch = if (pos < input.length) input[pos].code else -1
        }

        fun eat(charToEat: Int): Boolean {
            while (ch == ' '.code) nextChar()
            if (ch == charToEat) {
                nextChar()
                return true
            }
            return false
        }

        fun parse(): Double {
            nextChar()
            val x = parseExpression()
            if (pos < input.length) throw RuntimeException("Unexpected: " + ch.toChar())
            return x
        }

        // Grammar:
        // expression = term | expression `+` term | expression `-` term
        // term = factor | term `*` factor | term `/` factor | term `%` factor
        // factor = `+` factor | `-` factor | `(` expression `)` | number | functionFactor

        fun parseExpression(): Double {
            var x = parseTerm()
            while (true) {
                when {
                    eat('+'.code) -> x += parseTerm()
                    eat('-'.code) -> x -= parseTerm()
                    else -> return x
                }
            }
        }

        fun parseTerm(): Double {
            var x = parseFactor()
            while (true) {
                when {
                    eat('*'.code) -> x *= parseFactor()
                    eat('/'.code) -> {
                        val divisor = parseFactor()
                        if (divisor == 0.0) throw ArithmeticException("Division by zero")
                        x /= divisor
                    }
                    eat('%'.code) -> x %= parseFactor()
                    else -> return x
                }
            }
        }

        fun parseFactor(): Double {
            if (eat('+'.code)) return +parseFactor()
            if (eat('-'.code)) return -parseFactor()

            var x: Double
            val startPos = this.pos

            if (eat('('.code)) {
                x = parseExpression()
                eat(')'.code)
            } else if ((ch in '0'.code..'9'.code) || ch == '.'.code) {
                while ((ch in '0'.code..'9'.code) || ch == '.'.code) nextChar()
                x = input.substring(startPos, this.pos).toDouble()
            } else if (eat('π'.code)) {
                x = Math.PI
            } else if (eat('e'.code)) {
                x = Math.E
            } else if (eat('√'.code)) {
                if (eat('('.code)) {
                    val arg = parseExpression()
                    eat(')'.code)
                    if (arg < 0) throw RuntimeException("Negative root")
                    x = sqrt(arg)
                } else {
                    val factor = parseFactor()
                    if (factor < 0) throw RuntimeException("Negative root")
                    x = sqrt(factor)
                }
            } else if (ch in 'a'.code..'z'.code) {
                while (ch in 'a'.code..'z'.code) nextChar()
                val func = input.substring(startPos, this.pos)
                if (eat('('.code)) {
                    val arg = parseExpression()
                    eat(')'.code)
                    x = when (func) {
                        "sin" -> if (isRad) sin(arg) else sin(Math.toRadians(arg))
                        "cos" -> if (isRad) cos(arg) else cos(Math.toRadians(arg))
                        "tan" -> if (isRad) tan(arg) else tan(Math.toRadians(arg))
                        "sinh" -> sinh(arg)
                        "cosh" -> cosh(arg)
                        "tanh" -> tanh(arg)
                        "lg" -> log10(arg)
                        "ln" -> ln(arg)
                        "sqrt" -> sqrt(arg)
                        else -> throw RuntimeException("Unknown function: $func")
                    }
                } else {
                    x = when (func) {
                        "pi" -> Math.PI
                        "e" -> Math.E
                        else -> throw RuntimeException("Unknown identifier: $func")
                    }
                }
            } else {
                throw RuntimeException("Unexpected: " + ch.toChar())
            }

            return x
        }
    }
}
