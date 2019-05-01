package ru.coderedwolf.wordlearn

import androidx.fragment.app.Fragment
import ru.coderedwolf.wordlearn.ui.base.StubFragment
import ru.coderedwolf.wordlearn.ui.main.MainFlowFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

/**
 * @author CodeRedWolf. Date 21.04.2019.
 */
object Screens {

    object MainFlow : SupportAppScreen() {
        override fun getFragment() = MainFlowFragment()
    }

    object LearnScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return StubFragment()
        }
    }

    object WordsScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return StubFragment()
        }
    }

    object PhrasesScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return StubFragment()
        }
    }
}