package com.stolensugar.web

fun processTalon(talon: String): Map<String, String> {

    // Matches metadata at the top of a .talon file up to and including the single hyphen line "-"
    val talonMetadata = Regex("(?sm).*^-$")

    // Matches single or multiline Talon voice commands in a .talon file
    val talonCommandRegex =
        Regex("(?m)^[a-z A-Z._<>*+{}()|^$\\[\\]]+(?<!\\(\\)):.*(?:\\R([ \\t]+)\\S.*(?:\\R\\1\\S.*\$)*)?")

    return talonCommandRegex
        .findAll(talon.removePrefix(talonMetadata))
        .map { it.value.splitFirst(Regex(": *\\R?")) }
        .associate { it[0]
                    .removePrefix("^")
                    .removeSuffix("$") to
                it[1]
                    .trimIndent()
                    .lines()
                    .filterNot { it.startsWith("#") }
                    .joinToString(separator = "\n") }
}

fun processFormatters(lines: List<String>): Map<String, String> {
    val formatMappingRegex = Regex("([\"'])[A-Z a-z]+\\1\\s*:\\s*formatters_dict\\[([\"'])[A-Z_]+\\2\\]")

    return getChangedWords(lines, formatMappingRegex);
}

fun processKeys(lines: List<String>): Map<String, String> {
    val keyMappingRegex = Regex("([\"'])[A-Z a-z]+\\1\\s*:\\s*([\"']).+\\2")
    val alphabetRegex = Regex("([\"'])(?:[A-Za-z]+ ){25}[A-Za-z]+\\1\\.split")
    val numberRegex = Regex("([\"'])(?:[A-Za-z]+ ){9}[A-Za-z]+\\1\\.split")

    val changedWords = getChangedWords(lines, keyMappingRegex)
    changedWords.putAll(processLettersNumbers(lines, alphabetRegex))
    changedWords.putAll(processLettersNumbers(lines, numberRegex))

    return changedWords;
}

fun getChangedWords(lines: List<String>, regex: Regex): MutableMap<String, String> {
    val mappingLines = lines.filter { it.contains(regex) }
    val plusMappings = getPlusMappings(mappingLines, regex)
    val minusMappings = getMinusMappings(mappingLines, regex)
    val changedWords: MutableMap<String, String> = HashMap();

    for ((k, v) in minusMappings) {
        if (plusMappings.containsKey(v)) {
            changedWords[k] = plusMappings.getOrDefault(v, "")
        }
    }

    return changedWords;
}

fun getPlusMappings(lines: List<String>, mappingRegex: Regex): Map<String, String> {
    return lines
        .filter { it.startsWith('+') }
        .extractMappings(mappingRegex)
        .associate { it[1] to it[0] }
}

fun getMinusMappings(lines: List<String>, mappingRegex: Regex): Map<String, String> {
    return lines
        .filter { it.startsWith('-') }
        .extractMappings(mappingRegex)
        .associate { it[0] to it[1] }
}

fun processLettersNumbers(lines: List<String>, regex: Regex): Map<String, String> {
    val matchLines = lines.filter { it.contains(regex) }
    val hasPlus = matchLines.any { it.startsWith('+') }
    val hasMinus = matchLines.any { it.startsWith('-') }

    return if (matchLines.size == 2 && hasPlus && hasMinus ) {
        val plusList = getWordList(matchLines, regex, '+')
        val minusList = getWordList(matchLines, regex, '-')
        minusList.zip(plusList).filter { it.first != it.second }.toMap()
    } else {
        mapOf()
    }
}

fun getWordList(lines: List<String>, regex: Regex, prefix: Char): List<String> {
    val line = lines.filter { it.startsWith(prefix) }[0]
    val match = regex.find(line)?.value ?: ""

    return match.stripLettersNumbers()
        .split(" ")
}




