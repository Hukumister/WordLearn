package ru.coderedwolf.wordlearn.ui.base

import android.os.Bundle
import ru.coderedwolf.wordlearn.R
import ru.terrakok.cicerone.Router
import toothpick.Toothpick
import javax.inject.Inject

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 05.09.18.
 */
class StubFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_stub

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
    }

    override fun onBackPressed() {
        router.exit()
    }
}