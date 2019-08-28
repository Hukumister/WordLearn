package ru.coderedwolf.wordlearn.mainflow.ui

import ru.terrakok.cicerone.android.support.SupportAppScreen

class MainFlowScreens private constructor() {
    object WordsCategory : SupportAppScreen() {
        override fun getFragment() = WordsCategoryFragment()
    }

    object PhraseTopic : SupportAppScreen() {
        override fun getFragment() = PhraseTopicListFragment()
    }

    object Learn : SupportAppScreen() {
        override fun getFragment() = LearnFragment()
    }
}