package com.stolensugar.web

import com.stolensugar.web.dynamodb.models.SpokenFormUserModel

data class Command(val target: String, val invocation: String, val file: String, val context: String)

data class CommandGroupMappings(val invocationMap: Map<String, String>, val targetMap: Map<String, String>)

data class UserChanges(val userId: String, val branch: String, val baseChanges: MutableMap<String, MutableMap<String, String>>, val SpokenFormUsers: MutableMap<String, MutableMap<String, SpokenFormUserModel>>)