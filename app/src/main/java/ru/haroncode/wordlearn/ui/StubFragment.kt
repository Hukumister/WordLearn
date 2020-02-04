package ru.haroncode.wordlearn.ui

import ru.haroncode.wordlearn.R
import ru.haroncode.wordlearn.common.ui.BaseFragment
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class StubFragment : BaseFragment(R.layout.fragment_stub) {

    @Inject lateinit var router: Router

    override fun onBackPressed() = router.exit()
}