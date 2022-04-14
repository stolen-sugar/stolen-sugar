package com.stolensugar.web

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.kohsuke.github.*
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class GithubSandbox {
    private val watchFiles: MutableSet<String>
    private val baseInvocationMap: MutableMap<String, Command>
    private val baseTargetMap: MutableMap<String, Command>
    private val changedWords: MutableMap<String, MutableMap<String, String>>
    private val talonFilesProcessed: MutableSet<String>
//    private val baseCommands: BaseCommands
    private val commandGroups: Set<CommandGroup>
    private var commandGroupMap: MutableMap<String, CommandGroupMappings>
    private val github: GitHub
    private val gson: Gson
    private val changedWordsReversed: MutableMap<String, MutableMap<String, String>>

    init {
        watchFiles = HashSet()
        baseInvocationMap = HashMap()
        baseTargetMap = HashMap()
        changedWords = HashMap()
        talonFilesProcessed = HashSet()
        gson = GsonBuilder().setPrettyPrinting().create()
        val type = object : TypeToken<HashSet<CommandGroup?>?>() {}.type
//        val type = object : TypeToken<BaseCommands?>() {}.type
//        val mapType = object : TypeToken<Map<String, String>>() {}.type
        val jsonString = Files.readString(Path.of("src/main/resources/base_commands_core.json"))
//        baseCommands = gson.fromJson(jsonString, type)
//        println(gson.toJson(baseCommands))
        commandGroups = gson.fromJson(jsonString, type)
        commandGroupMap = HashMap()
        github = GitHubBuilder.fromPropertyFile().build()
        changedWordsReversed = HashMap()
    }

    @Throws(IOException::class)
    fun run() {
        populate()
        val talonRepo = github.getRepository("knausj85/knausj_talon")
        val baseCommitShas = talonRepo.queryCommits()
            .pageSize(100)
            .list()
            .map { x: GHCommit -> x.shA1 }
            .toSet()

        val ronRepo = github.getRepository("RonWalker22/ron_talon")
//        val ronRepo = github.getRepository("daslater/knausj_talon")
        val ronCommits = ronRepo.queryCommits()
            .pageSize(100)
            .list()
            .toList()
            .reversed()

//        for (file in watchFiles) {
//            println(file)
//        }

        for (commit in ronCommits) {
            if (commit.shA1 !in baseCommitShas) {
                for (commitFile in commit.files) {
//                    println(commitFile.fileName)
                    if (commitFile.fileName in watchFiles) {
//                        println(commitFile.fileName)
//                        println(commitFile.patch)
//                        println("-------------------------------------------------------------------------")
                        if (commitFile.fileName.endsWith(".talon")) {
                            val talonFile = ronRepo.getFileContent(commitFile.fileName)
                            analyzeTalon(talonFile)
                        } else if (commitFile.fileName.endsWith(".py")) {
                            analyzePython(commitFile)
                        }
                    }
                }
            }
        }
        println(gson.toJson(changedWords.filterNot { it.value.isEmpty() }))
    }

    @Throws(IOException::class)
    private fun populate() {
        for ((file, context, commands) in commandGroups) {
            watchFiles.add(file)
            val targetMap = commands
                .map { (k, v) -> Pair(v, k) }
                .toMap()
            commandGroupMap[file] = CommandGroupMappings(invocationMap = commands, targetMap = targetMap)
            for (invocation in commands.keys) {
                baseInvocationMap[invocation] = Command(
                    commands[invocation]!!, invocation,
                    file, context
                )
            }
        }
    }

    private fun analyzeTalon(talon: GHContent) {

        println(talon.path)
        println("--------------------------------------")
        val talonCommands = processTalon(String(talon.read().readAllBytes()))
        val baseTargets = commandGroupMap[talon.path]!!.targetMap
        val baseInvocations = commandGroupMap[talon.path]!!.invocationMap
        val localChanges: MutableMap<String, String> = HashMap()

//        println("Talon Commands")
//        println(gson.toJson(talonCommands))
//        println("--------------------------------------")
//        println("Base Commands")
//        println(gson.toJson(baseInvocations))
//        println("--------------------------------------")
//        println("Base commands same? ${baseInvocations == talonCommands}")
//        println("Differences:")
//        println("Old:")
//        for ((k, v) in (baseInvocations.entries - talonCommands.entries)) {
//            println("$k: $v")
//        }
//        println("New: ")
//        for ((k, v) in (talonCommands.entries - baseInvocations.entries)) {
//            println("$k: $v")
//        }
        for ((key, value) in talonCommands.iterator()) {
            if (!baseInvocations.containsKey(key)) {
//                println("Old: ${baseTargets.getOrDefault(value, "")}")
//                println("New: ${key}")
                if (baseTargets.containsKey(value)) {
                    val oldWord: String = baseTargets.getOrDefault(value, "")
                    val newWord = key
                    localChanges[oldWord] = newWord
                }
            }
        }

        changedWords[talon.path] = localChanges

        println("--------------------------------------")
        println("--------------------------------------")
        println("--------------------------------------")
    }

    private fun analyzePython(commitFile: GHCommit.File) {
        val patch = commitFile.patch
        val lines = patch.split("\\n".toRegex())
            .filter { it.startsWith(listOf("+", "-")) }
            .filterNot { it.matches(Regex("[+-]\\s*#.*")) } // Remove comments

        println("--------------------------------------")
        println(commitFile.fileName)
        if (commitFile.fileName == "code/formatters.py") {
            processChanges(commitFile.fileName, processFormatters(lines))
        } else if (commitFile.fileName == "code/keys.py") {
            processChanges(commitFile.fileName, processKeys(lines))
        }
        println("--------------------------------------")
    }

    fun processChanges(file: String, changes: Map<String, String>) {
        if (changedWords.containsKey(file)) {
            for ((old, new) in changes) {
                if (changedWordsReversed[file]?.containsKey(old) == true) {
                    val oldest = changedWordsReversed[file]?.getOrDefault(old, "") ?: ""
                    changedWordsReversed[file]!![new] = oldest
                    changedWords[file]!![oldest] = new
                } else {
                    changedWords[file]!![old] = new
                    changedWordsReversed[file]!![new] = old
                }
            }
        } else {
            changedWords[file] = changes.toMutableMap()
            changedWordsReversed[file] = changes.entries
                .associate { it.value to it.key }
                .toMutableMap()
        }
    }
}