package ru.coderedwolf.wordlearn.presentation.launcher

import moxy.InjectViewState
import ru.coderedwolf.wordlearn.Screens
import ru.coderedwolf.wordlearn.domain.interactors.init.PrePopulateDataBaseInteractor
import ru.coderedwolf.wordlearn.presentation.base.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 20.06.2019.
 */
@InjectViewState
class Launcher @Inject constructor(
        private val router: Router,
        private val prePopulateDataBaseInteractor: PrePopulateDataBaseInteractor
) : BasePresenter<LauncherView>() {

    fun onCouldStart() = launchUI {
        val shouldInit = prePopulateDataBaseInteractor.shouldInit()
        if (shouldInit) {
            viewState.blockLoading(true)
            prePopulateDataBaseInteractor.prePopulateDataBase()
            viewState.blockLoading(false)
        }
        router.newRootChain(Screens.MainScreen)
    }
}