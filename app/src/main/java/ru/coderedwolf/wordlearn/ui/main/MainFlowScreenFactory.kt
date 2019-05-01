package ru.coderedwolf.wordlearn.ui.main

import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.Screens
import ru.terrakok.cicerone.android.support.SupportAppScreen

/**
 * @author CodeRedWolf. Date 01.05.2019.
 */
class MainFlowScreenFactory {

    fun findScreen(itemId: Int): SupportAppScreen {
        return when (itemId) {
            R.id.words -> {
                Screens.WordsScreen
            }
            R.id.phrases -> {
                Screens.PhrasesScreen
            }
            R.id.learn -> {
                Screens.LearnScreen
            }
            else -> Screens.WordsScreen
        }
    }
}