package com.stolensugar.web

fun String.startsWith(prefixes: List<String>): Boolean {
    return prefixes.any { this.startsWith(it) }
}

fun String.splitFirst(separator: Regex): List<String> {
    val splits = this.split(separator)
    val rest = splits.subList(1, splits.size).joinToString(separator = "")

    return listOf(splits[0], rest)
}

@kotlin.ExperimentalStdlibApi
fun String.startsWith(regex: Regex): Boolean {
    return regex.matchesAt(this, 0)
}

fun String.removePrefix(regex: Regex): String {
    val prefix = regex.find(this)
    return this.removePrefix(prefix?.value ?: "")
}

fun String.removeSuffix(regex: Regex): String {
    val suffix = regex.find(this)
    return this.removeSuffix(suffix?.value ?: "")
}

fun List<String>.extractMappings(regex: Regex): List<List<String>> {
    return this.map { regex
        .find(it)?.value ?: "" }
        .mapTo(mutableListOf()) { it
            .splitFirst(Regex("\\s*:\\s*"))
            .mapTo(mutableListOf()){ it
                .removePrefix(Regex("[\"']"))
                .removeSuffix(Regex("[\"']")) } }
}

fun String.stripLettersNumbers(): String {
    return this.removePrefix(Regex("[\"']"))
        .removeSuffix(Regex("[\"']\\.split"))
}