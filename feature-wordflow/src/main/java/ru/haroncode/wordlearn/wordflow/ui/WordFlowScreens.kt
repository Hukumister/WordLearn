package ru.haroncode.wordlearn.wordflow.ui

import ru.terrakok.cicerone.android.support.SupportAppScreen

class WordFlowScreens private constructor() {

    object WordList : SupportAppScreen() {
        override fun getFragment() = WordListFragment()
    }

    object CreateWord : SupportAppScreen() {
        override fun getFragment() = CreateWordFragment()
    }
}