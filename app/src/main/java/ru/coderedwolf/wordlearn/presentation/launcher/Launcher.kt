package ru.coderedwolf.wordlearn.presentation.launcher

import moxy.InjectViewState
import ru.coderedwolf.wordlearn.common.presentation.BasePresenter
import ru.coderedwolf.wordlearn.domain.interactors.init.PrePopulateDataBaseInteractor
import ru.coderedwolf.wordlearn.ui.Flows
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author CodeRedWolf. Date 20.06.2019.
 */
@Singleton
@InjectViewState
class Launcher @Inject constructor(
    private val router: Router,
    private val prePopulateDataBaseInteractor: PrePopulateDataBaseInteractor
) : BasePresenter<LauncherView>() {

    fun onColdStart() = launchUI {
        val shouldInit = prePopulateDataBaseInteractor.shouldInit()
        if (shouldInit) {
            viewState.blockLoading(true)
            prePopulateDataBaseInteractor.prePopulateDataBase()
            viewState.blockLoading(false)
        }
        router.newRootChain(Flows.Main)
    }
}