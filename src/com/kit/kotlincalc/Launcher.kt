package com.kit.kotlincalc

import com.kit.kotlincalc.tests.launchTests
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.geometry.Rectangle2D
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Screen
import javafx.stage.Stage

class Launcher : Application() {

    object Settings {
        val isDebugMode: Boolean = true
        val printIntermediateResults: Boolean = true
        val printGroupHierarchy: Boolean = true
        val resultPrecision: Int = 3
    }

    override fun start(primaryStage: Stage?) {
        if(Settings.isDebugMode) {
            launchTests()
        } else {
            val root: Parent? = FXMLLoader.load(Launcher::class.java.getResource("/calc_screen.fxml"))
            val primaryScreenBounds: Rectangle2D = Screen.getScreens()[1].visualBounds

            primaryStage?.x = primaryScreenBounds.minX + 500
            primaryStage?.y = primaryScreenBounds.minY + 250

            primaryStage?.title = "Kotlin + JavaFX calc test"
            primaryStage?.scene = Scene(root)
            primaryStage?.show()
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Launcher::class.java)
        }
    }
}