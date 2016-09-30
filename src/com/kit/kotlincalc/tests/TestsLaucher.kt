package com.kit.kotlincalc.tests

fun launchTests() {
    val testCases: List<TestCase> = listOf(
        TestCase("2 + 2 * 2", 6.0),
        TestCase("(2 + 2) * 2", 8.0),
        TestCase("(2 + 2) * (3 + -3)", 0.0),
        TestCase("-3.5 + -2 + 5", -0.5),
        TestCase("10 * 10 / 10", 10.0),

        // todo: Plus is first, change algorithm
        TestCase("0.1 - 0.2 + 0.3", 0.2), // -
        TestCase("2 / 2 * 3", 3.0), // -

        TestCase("(2 - 2) / 10", 0.0),
        TestCase("(2 + (2 + 3)) + (2 - 2) + (2 * 2) + (2 / 2)", 12.0),
        TestCase("2 / 2 + -3.5", -2.5)
    )

    for(testCase in testCases) {
        testCase.doTest()
        println("=============================")
    }
}
