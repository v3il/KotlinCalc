package com.kit.kotlincalc

import com.kit.kotlincalc.operations.OperationsManager
import java.math.BigDecimal
import java.util.regex.Pattern

class Group(val level: Int, val groupContent: String, val startIndex: Int, val endIndex: Int) {

    val contentLength: Int = groupContent.length

    var innerGroups: MutableList<Group> = mutableListOf()
    var subRegions: MutableList<IntRange> = mutableListOf()

    var groupContentBuilder: StringBuilder = StringBuilder(groupContent)
    var replacedContentBuilder: StringBuilder = StringBuilder()

    val operationsManager: OperationsManager = OperationsManager()

    init {
        splitContentIntoSubGroups()
    }

    fun splitContentIntoSubGroups() {
        var openBracketIndex: Int = 0
        var bracketCounter: Int = 0

        /**
         * Find all outer open and close brackets in string.
         * Content in these brackets is a content of new inner group
         */
        for(index in groupContentBuilder.indices) {
            val charAtIndex: Char = groupContentBuilder[index]

            if(charAtIndex == '(') {
                if(bracketCounter == 0) {
                    openBracketIndex = index
                }

                bracketCounter++
            }

            if(charAtIndex == ')') {
                bracketCounter--

                if(bracketCounter == 0) {
                    /**
                     * Got range of a content of a new child group
                     */
                    subRegions.add(IntRange(openBracketIndex, index))
                }
            }
        }

        /**
         * Replace selected ranges to group ids ({groupNum})
         */
        var originalContentIndex: Int = 0

        for(childContentIndicesRange in subRegions) {
            val childGroupContent: String = groupContentBuilder.substring(
                /**
                 * Exclude open bracket from child group content
                 */
                childContentIndicesRange.start + 1,
                childContentIndicesRange.endInclusive
            )

            /**
             * Append expression content between previous and next inner groups content
             */
            replacedContentBuilder
                .append(
                    groupContentBuilder.substring(
                        originalContentIndex, childContentIndicesRange.start
                    )
                ).append(
                    /**
                     * Replaced content now is a inner group id
                     */
                    buildInnerGroupId(buildNextInnerGroupIndex())
                )

            /**
             * Store previous group's end index in originalContentIndex
             */
            originalContentIndex = childContentIndicesRange.endInclusive + 1

            /**
             * Create new inner group
             */
            val childGroup: Group = Group(
                level + 1,
                childGroupContent,
                childContentIndicesRange.start,
                childContentIndicesRange.endInclusive + 1
            )

            innerGroups.add(childGroup)
        }

        /**
         * Append to StringBuilder with replaced string, tail of original string
         * if it exists. For example, in (2 + 2) - 1, " - 1" will be added by code below
         */
        replacedContentBuilder.append(
            groupContentBuilder.substring(
                originalContentIndex,
                groupContent.length
            )
        )
    }

    fun getGroupResult(): BigDecimal {
        /**
         * Calculate each child group's result and replace group's id with this result
         */
        for(childGroupIndex in innerGroups.indices) {
            val group: Group = innerGroups[childGroupIndex]
            val groupId: String = buildInnerGroupId(childGroupIndex)
            val groupIdStartIndex: Int = replacedContentBuilder.indexOf(buildInnerGroupId(childGroupIndex))
            val groupIdEndIndex = groupIdStartIndex + groupId.length

            replacedContentBuilder.replace(groupIdStartIndex, groupIdEndIndex, group.getGroupResult().toString())
        }

        /**
         * Calculate expression with simple operations
         */
        var expression: String = replacedContentBuilder.toString()
        val result: StringBuffer = StringBuffer()

        for(operation in operationsManager.operationPriorities) {
            val p = Pattern.compile(operation.pattern)
            var groupCounter: Int = -1

            while(groupCounter != 0) {
                result.setLength(0)

                val m = p.matcher(expression)

                groupCounter = 0

                while (m.find()) {
                    groupCounter++
                    val group: String = m.group()
                    val groupResult = ExpressionPart(group).evaluatePart()

                    m.appendReplacement(result, groupResult)
                }

                m.appendTail(result)
                expression = result.toString()
            }
        }

        return BigDecimal.valueOf(result.toString().toDouble())
    }

    fun buildNextInnerGroupIndex(): Int {
        return innerGroups.size
    }

    fun buildInnerGroupId(innerGroupIndex: Int): String {
        return "{$innerGroupIndex}"
    }

    fun printHierarchy() {
        println(this)

        for(group in innerGroups) {
            group.printHierarchy()
        }
    }

    override fun toString(): String {
        return getLevelIndentation() + groupContent + " => " + replacedContentBuilder
    }

    fun getLevelIndentation(): String {
        var index: Int = 0
        val indentation: StringBuilder = StringBuilder()

        while(index < level) {
            indentation.append("..")
            index++
        }

        return indentation.toString()
    }

}