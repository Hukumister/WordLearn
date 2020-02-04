package ru.haroncode.wordlearn.ui

import ru.haroncode.wordlearn.mainflow.ui.MainFlowFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Flows private constructor() {

    object Main : SupportAppScreen() {
        override fun getFragment() = MainFlowFragment()
    }

    object Stub : SupportAppScreen() {
        override fun getFragment() = StubFragment()
    }
}
