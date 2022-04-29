package search

import java.io.File

fun main(args: Array<String>) {
    val file = File(args[1])
    val database = file.readLines().toList()
    val databaseGrouped = mutableListOf<List<String>>()
    for (index in database.indices) { databaseGrouped.add(database[index].split(" ").map { it.uppercase() }) }
    menu(database, databaseGrouped)
}

fun menu(database: List<String>, databaseGrouped: MutableList<List<String>>) {
    while (true) {
        println("""
            === Menu ===
            1. Find a person
            2. Print all people
            0. Exit
        """.trimIndent())
        val inputNumber = readln().toIntOrNull()
        println()
        when (inputNumber) {
            1 -> {
                databaseSearch(database, databaseGrouped)
                println()
            }
            2 -> {
                println("=== List of people ===")
                database.forEach { println(it) }
                println()
            }
            0 -> {
                println("Bye!")
                break
            }
            else -> println("Incorrect option! Try again.\n" )
        }
    }
}

fun databaseSearch(database: List<String>, databaseGrouped: MutableList<List<String>>) {
    println("Select a matching strategy: ALL, ANY, NONE")
    val select = when (readln().uppercase()) {
        "ALL" -> 1
        "ANY" -> 2
        "NONE" -> 3
        else -> null
    } ?: return
    println("Enter a name or email to search all suitable people.")
    val query = readln().split(" ").map { it.uppercase() }
    when {
        select != 3 && !query.any { it in databaseGrouped.flatten() } -> {
            println("No matching people found.")
        }
        select == 3 -> {
            val result = mutableListOf<String>()
            for (index in database.indices) {
                if (query.any { it in databaseGrouped[index] } ) { continue }
                result.add(database[index])
            }
            println("${result.size} Person${if(result.size > 1) "s" else ""} found:")
            result.forEach { println(it) }
        }
        select == 2 -> {
            val result = mutableListOf<String>()
            for (index in database.indices) {
                if (query.any { it in databaseGrouped[index] } ) {
                    result.add(database[index])
                }
            }
            println("${result.size} Person${if(result.size > 1) "s" else ""} found:")
            result.forEach { println(it) }
        }
        select == 1 -> {
            val result = mutableListOf<String>()
            for (index in database.indices) {
                if (query.all { it in databaseGrouped[index] } ) {
                    result.add(database[index])
                }
            }
            println("${result.size} Person${if(result.size > 1) "s" else ""} found:")
            result.forEach { println(it) }
        }
    }
}
