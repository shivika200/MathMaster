
package com.example.mathmaster

import com.example.mathmaster.model.MathQuestion
import com.example.mathmaster.utils.MathUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MathUtilsTest {

    @Test
    fun correctAnswerReturnsTrue() {

        val result = MathUtils.isAnswerCorrect(
            selectedAnswer = "56",
            correctAnswer = "56"
        )

        assertTrue(result)
    }

    @Test
    fun incorrectAnswerReturnsFalse() {

        val result = MathUtils.isAnswerCorrect(
            selectedAnswer = "54",
            correctAnswer = "56"
        )

        assertFalse(result)
    }

    @Test
    fun answerComparisonIgnoresExtraSpaces() {

        val result = MathUtils.isAnswerCorrect(
            selectedAnswer = " 56 ",
            correctAnswer = "56"
        )

        assertTrue(result)
    }

    @Test
    fun answerComparisonIgnoresLetterCase() {

        val result = MathUtils.isAnswerCorrect(
            selectedAnswer = "True",
            correctAnswer = "true"
        )

        assertTrue(result)
    }

    @Test
    fun accuracyIsCalculatedCorrectly() {

        val result = MathUtils.calculateAccuracy(
            correctAnswers = 8,
            totalAttempts = 10
        )

        assertEquals(80f, result, 0.01f)
    }

    @Test
    fun zeroAttemptsReturnsZeroAccuracy() {

        val result = MathUtils.calculateAccuracy(
            correctAnswers = 0,
            totalAttempts = 0
        )

        assertEquals(0f, result, 0.01f)
    }

    @Test
    fun invalidStatisticsReturnZeroAccuracy() {

        val result = MathUtils.calculateAccuracy(
            correctAnswers = 12,
            totalAttempts = 10
        )

        assertEquals(0f, result, 0.01f)
    }

    @Test
    fun validQuestionReturnsTrue() {

        val question = MathQuestion(
            category = "Multiplication",
            question = "What is 7 × 8?",
            correctAnswer = "56",
            explanation = "7 multiplied by 8 equals 56."
        )

        assertTrue(MathUtils.isValidQuestion(question))
    }

    @Test
    fun questionWithBlankTextReturnsFalse() {

        val question = MathQuestion(
            category = "Multiplication",
            question = "",
            correctAnswer = "56",
            explanation = "7 multiplied by 8 equals 56."
        )

        assertFalse(MathUtils.isValidQuestion(question))
    }
}
