package com.kit.kotlincalc.operations

class OperationsManager {

    val operationPriorities: List<Operation>
    val numberStringPattern: String = "-?\\d+(\\.\\d*)?"

    init {
        operationPriorities = listOf(
            Operation("Множення", buildOperationPattern("\\*")),
            Operation("Ділення", buildOperationPattern("/")),
            Operation("Додавання", buildOperationPattern("\\+")),
            Operation("Віднімання", buildOperationPattern("\\-"))
        )
    }

    fun buildOperationPattern(operation: String): String {
        return "$numberStringPattern\\s*$operation\\s*$numberStringPattern"
    }
}