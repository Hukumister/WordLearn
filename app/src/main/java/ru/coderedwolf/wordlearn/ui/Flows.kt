package ru.coderedwolf.wordlearn.ui

import ru.coderedwolf.wordlearn.mainflow.ui.MainFlowFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Flows private constructor() {

    object Main : SupportAppScreen() {
        override fun getFragment() = MainFlowFragment()
    }

}