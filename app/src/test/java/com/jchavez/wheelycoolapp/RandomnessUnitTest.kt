package com.jchavez.wheelycoolapp

import com.jchavez.wheelycoolapp.utils.CircleCalculator
import org.junit.Test

class RandomnessUnitTest {
    var resultGroups = arrayListOf<ArrayList<Float>>()
    var numbersOfItemsToTest = arrayListOf<Int>()

    private fun getMaxBoundary(numberOfItems: Int): Float {
        return (1000.0f / numberOfItems.toFloat()) + ((1000.0f / 2.0f) * 0.10f)
    }

    private fun getMinBoundary(numberOfItems: Int): Float {
        return (1000.0f / numberOfItems.toFloat()) - ((1000.0f / 2.0f) * 0.10f)
    }

    private fun setup() {
        numbersOfItemsToTest = arrayListOf(2, 3, 4, 5, 6, 7, 8)
        numbersOfItemsToTest.forEachIndexed { index, element ->
            resultGroups.add(arrayListOf())
            for (i in 0..999) {
                val result = CircleCalculator.generateRandomRotationValue(element)
                resultGroups[index].add(result)
            }
        }
    }

    @Test
    fun testAllValuesEvenlyReturned() {
        setup()
        resultGroups.forEachIndexed { index, resultGroup ->
            val resultsWithValues = resultGroup.groupingBy { it }.eachCount()
            val maxBoundary = getMaxBoundary(numbersOfItemsToTest[index])
            val minBoundary = getMinBoundary(numbersOfItemsToTest[index])
            resultsWithValues.forEach {
                if (it.value > maxBoundary || it.value < minBoundary) {
                    assert(false)
                }
            }
        }
        assert(true)
    }
}
