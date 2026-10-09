package com.example

import com.example.engine.MathEvaluator
import org.junit.Assert.assertEquals
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testStandardAddition() {
        val result = MathEvaluator.evaluate("12 + 28")
        assertEquals("40", result)
    }

    @Test
    fun testStandardPrecedence() {
        val result = MathEvaluator.evaluate("10 + 5 × 2")
        assertEquals("20", result)
    }

    @Test
    fun testScientificSqrt() {
        val result = MathEvaluator.evaluate("√(144)")
        assertEquals("12", result)
    }

    @Test
    fun testScientificLog() {
        val result = MathEvaluator.evaluate("lg(1000)")
        assertEquals("3", result)
    }

    @Test
    fun testDivideByZero() {
        val result = MathEvaluator.evaluate("10 ÷ 0")
        assertEquals("Error", result)
    }
}
