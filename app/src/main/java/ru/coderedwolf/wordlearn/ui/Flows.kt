package ru.coderedwolf.wordlearn.ui

import ru.coderedwolf.learnword.learnphrasesflow.ui.LearnPhrasesFlowFragment
import ru.coderedwolf.wordlearn.learnwordsflow.ui.LearnWordsFlowFragment
import ru.coderedwolf.wordlearn.mainflow.ui.MainFlowFragment
import ru.coderedwolf.wordlearn.wordflow.ui.WordFlowFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Flows private constructor() {

    object Main : SupportAppScreen() {
        override fun getFragment() = MainFlowFragment()
    }

    object LearnWords : SupportAppScreen() {
        override fun getFragment() = LearnWordsFlowFragment()
    }

    object LearnPhrases : SupportAppScreen() {
        override fun getFragment() = LearnPhrasesFlowFragment()
    }

    class Word(
        val categoryId: Long,
        val categoryName: String
    ) : SupportAppScreen() {
        override fun getFragment() = WordFlowFragment.newInstance(
            categoryId = categoryId,
            categoryName = categoryName
        )
    }

    object Stub : SupportAppScreen() {
        override fun getFragment() = StubFragment()
    }
}