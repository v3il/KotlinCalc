package com.kit.kotlincalc.tests

import com.kit.kotlincalc.Group
import com.kit.kotlincalc.Launcher
import java.math.BigDecimal

class TestCase(val rootGroupContent: String, val realResult: Double) {

    fun doTest() {
        val rootGroup: Group = Group(0, rootGroupContent, 0, rootGroupContent.length)
        val groupResult: BigDecimal = rootGroup.getGroupResult()
        val result: BigDecimal = BigDecimal.valueOf(realResult)

        println()

        if(Launcher.Settings.printGroupHierarchy) {
            rootGroup.printHierarchy()
        }

        println("\n$rootGroupContent = $realResult ($groupResult) : ${result.compareTo(groupResult) == 0}\n\n")
    }
}