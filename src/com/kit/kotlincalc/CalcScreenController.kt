package com.kit.kotlincalc

import com.kit.kotlincalc.operations.OperationsManager
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextField

class CalcScreenController {

    lateinit @FXML var result: TextField

    val operationsManager: OperationsManager

    init {
        operationsManager = OperationsManager()
    }

    fun handleButtonClick(actionEvent: ActionEvent) {
        val clickedButton: Button = actionEvent.source as Button
        result.text += clickedButton.text
    }

    fun showResult() {
        result.text = parseExpression()
    }

    fun parseExpression(): String {
        val expression: String = result.text
        val rootGroup: Group = Group(0, expression, 0, expression.length)

        return expression + " = " + rootGroup.getGroupResult().toString()
    }
}