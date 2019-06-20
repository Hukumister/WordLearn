package ru.coderedwolf.wordlearn

import androidx.fragment.app.Fragment
import ru.coderedwolf.wordlearn.ui.base.StubFragment
import ru.coderedwolf.wordlearn.ui.learn.LearnMainFragment
import ru.coderedwolf.wordlearn.ui.learn.LearnPhrasesFragment
import ru.coderedwolf.wordlearn.ui.main.MainFlowFragment
import ru.coderedwolf.wordlearn.ui.phrase.PhraseTopicListFragment
import ru.coderedwolf.wordlearn.ui.word.WordFlowFragment
import ru.coderedwolf.wordlearn.ui.word.createword.CreateWordFragment
import ru.coderedwolf.wordlearn.ui.word.wordlist.WordListFragment
import ru.coderedwolf.wordlearn.ui.word.category.WordsCategoryFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

/**
 * @author CodeRedWolf. Date 21.04.2019.
 */
object Screens {

    object MainScreen : SupportAppScreen() {
        override fun getFragment() = MainFlowFragment()
    }

    object WordsCategoryScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = WordsCategoryFragment()
    }

    object PhraseTopicScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = PhraseTopicListFragment()
    }

    class PhraseFlowScreen(val topicId: Long) : SupportAppScreen() {
        override fun getFragment(): Fragment = StubFragment()
    }

    class WordScreenFlow(val categoryId: Long, val categoryName: String) : SupportAppScreen() {
        override fun getFragment() = WordFlowFragment.newInstance(categoryId, categoryName)
    }

    object WordListScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = WordListFragment()
    }

    object WordCreateScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = CreateWordFragment()
    }

    object LearnMainScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = LearnMainFragment()
    }

    object LearnWordsScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = StubFragment()
    }

    object LearnPhrasesScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = LearnPhrasesFragment()
    }
}