package ru.coderedwolf.wordlearn.di

/**
 * @author CodeRedWolf. Date 01.05.2019.
 */
object ScopeManager {

    private val scopeNames = mutableSetOf<String>()

    fun exist(scopeName: String): Boolean {
        return scopeNames.contains(scopeName)
    }

    fun addName(scopeName: String) {
        scopeNames.add(scopeName)
    }

    fun removeName(scopeName: String) {
        scopeNames.remove(scopeName)
    }
}

