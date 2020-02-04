package ru.coderedwolf.wordlearn.ui

import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.common.ui.BaseFragment
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class StubFragment : BaseFragment(R.layout.fragment_stub) {

    @Inject lateinit var router: Router

    override fun onBackPressed() = router.exit()
}