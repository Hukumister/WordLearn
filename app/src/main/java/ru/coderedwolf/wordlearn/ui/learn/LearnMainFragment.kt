package ru.coderedwolf.wordlearn.ui.learn

import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_learn_main.*
import ru.coderedwolf.wordlearn.extension.onClick
import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.Screens
import ru.coderedwolf.wordlearn.ui.base.BaseFragment
import ru.terrakok.cicerone.Router
import toothpick.Toothpick
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 14.06.2019.
 */
class LearnMainFragment : BaseFragment() {

    override val layoutRes: Int
        get() = R.layout.fragment_learn_main

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        wordButton.onClick { router.navigateTo(Screens.LearnWordsScreen) }
        phraseButton.onClick { router.navigateTo(Screens.LearnPhrasesScreen) }
    }

    override fun onBackPressed() = router.finishChain()
}