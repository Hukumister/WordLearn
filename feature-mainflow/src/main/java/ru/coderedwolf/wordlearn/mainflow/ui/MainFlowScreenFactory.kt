package ru.coderedwolf.wordlearn.mainflow.ui

import ru.coderedwolf.wordlearn.mainflow.R
import ru.terrakok.cicerone.android.support.SupportAppScreen

/**
 * @author CodeRedWolf. Date 01.05.2019.
 */
class MainFlowScreenFactory {

    fun findScreen(itemId: Int): SupportAppScreen = when (itemId) {
        R.id.words -> MainFlowScreens.WordsCategory
        R.id.phrases -> MainFlowScreens.PhraseTopic
        R.id.learn -> MainFlowScreens.Learn
        else -> MainFlowScreens.WordsCategory
    }
}