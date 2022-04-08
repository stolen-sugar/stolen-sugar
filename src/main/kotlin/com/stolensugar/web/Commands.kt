package com.stolensugar.web

data class Command(val target: String, val invocation: String, val file: String, val context: String)

data class CommandGroup(val file: String, val context: String, val commands: Map<String, String>)

data class BaseCommands(val repo_id: String, val user_id: String, val timestamp: String, val branch: String, val commandGroups: Set<CommandGroup?>?)

data class CommandMap(val invocationMap: Map<String, String>, val targetMap: Map<String, String>)