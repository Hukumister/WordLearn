package ru.coderedwolf.wordlearn.mainflow.presentation.set

/**
 * @author CodeRedWolf. Date 29.10.2019.
 */

data class WordSetUserViewState(
    val name: String = "",
    val age: Int = 0
) {

    sealed class Item {

        object AddWordSetItem : Item()

    }
}
