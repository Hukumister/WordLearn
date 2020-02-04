package ru.haroncode.wordlearn.mainflow.ui

import ru.haroncode.wordlearn.mainflow.ui.word.set.WordSetFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object MainFlowScreens {
    object WordSet : SupportAppScreen() {
        override fun getFragment() = WordSetFragment.newInstance()
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
