package ru.coderedwolf.wordlearn.mainflow.ui

import ru.terrakok.cicerone.android.support.SupportAppScreen

object MainFlowScreens {
    object WordsCategory : SupportAppScreen() {
        override fun getFragment() = StubFragment()
    }

    object PhraseTopic : SupportAppScreen() {
        override fun getFragment() = StubFragment()
    }

    object Learn : SupportAppScreen() {
        override fun getFragment() = LearnFragment()
    }

    object Search : SupportAppScreen() {
        override fun getFragment() = StubFragment()
    }

    object Settings : SupportAppScreen() {
        override fun getFragment() = StubFragment()
    }
}