package com.stolensugar.web

import com.stolensugar.web.dynamodb.models.SpokenFormUserModel

data class Command(val target: String, val invocation: String, val file: String, val context: String)

data class CommandGroup(val file: String, val context: String, val commands: Map<String, String>)

data class BaseCommands(val repo_id: String, val user_id: String, val timestamp: String, val branch: String, val commandGroups: Set<CommandGroup?>?)

data class CommandGroupMappings(val invocationMap: Map<String, String>, val targetMap: Map<String, String>)

data class UserChanges(var baseChanges: MutableMap<String, MutableMap<String, String>>, var customChanges: MutableMap<String, MutableMap<String, String>>, var SpokenFormUsers: MutableList<SpokenFormUserModel>)