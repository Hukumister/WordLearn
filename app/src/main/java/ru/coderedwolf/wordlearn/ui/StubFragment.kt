package ru.coderedwolf.wordlearn.ui

import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.common.ui.BaseFragment
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 05.09.18.
 */
class StubFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_stub

    @Inject
    lateinit var router: Router

    override fun onBackPressed() {
        router.exit()
    }
}