package ru.coderedwolf.wordlearn.mainflow.presentation.set

import io.reactivex.Observable
import ru.coderedwolf.mvi.core.BaseViewModelStore
import ru.coderedwolf.mvi.core.Middleware
import ru.coderedwolf.mvi.core.Navigator
import ru.coderedwolf.mvi.core.Reducer
import ru.coderedwolf.wordlearn.common.domain.system.SchedulerProvider
import ru.coderedwolf.wordlearn.mainflow.presentation.set.WordSetUserViewModel.Action
import ru.coderedwolf.wordlearn.mainflow.presentation.set.WordSetUserViewModel.Effect

/**
 * @author CodeRedWolf. Date 11.10.2019.
 */
class WordSetUserViewModel(
    schedulerProvider: SchedulerProvider
) : BaseViewModelStore<Action, WordSetUserViewState, Effect, Unit>(
    initialState = WordSetUserViewState(),
    reducer = ReducerImpl(),
    navigator = NavigatorImpl(),
    schedulerProvider = schedulerProvider,
    middleware = MiddleWareImpl()
) {


    sealed class Effect {

    }

    sealed class Action {

    }

    override fun onNavigationEvent(event: Unit) = Unit

    private class ReducerImpl : Reducer<WordSetUserViewState, Effect> {

        override fun invoke(state: WordSetUserViewState, effect: Effect): WordSetUserViewState {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    private class NavigatorImpl : Navigator<WordSetUserViewState, Effect, Unit> {

        override fun invoke(state: WordSetUserViewState, effect: Effect): Unit? {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    private class MiddleWareImpl : Middleware<Action, WordSetUserViewState, Effect> {

        override fun invoke(action: Action, state: WordSetUserViewState): Observable<Effect> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

}







