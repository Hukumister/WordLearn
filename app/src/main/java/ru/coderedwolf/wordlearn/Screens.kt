package ru.coderedwolf.wordlearn

import androidx.fragment.app.Fragment
import ru.coderedwolf.wordlearn.ui.base.StubFragment
import ru.coderedwolf.wordlearn.ui.main.MainFlowFragment
import ru.coderedwolf.wordlearn.ui.wordscategory.WordsCategoryFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

/**
 * @author CodeRedWolf. Date 21.04.2019.
 */
object Screens {

    object MainScreen : SupportAppScreen() {
        override fun getFragment() = MainFlowFragment()
    }

    object LearnScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = StubFragment()
    }

    object WordsCategoryScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = WordsCategoryFragment()
    }

    object PhrasesScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = StubFragment()
    }

    class CategoryWordsFlow(val categoryId: Long) : SupportAppScreen() {

        override fun getFragment() = StubFragment()
    }
}