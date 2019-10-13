package ru.coderedwolf.wordlearn.mainflow.presentation.set

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import ru.coderedwolf.wordlearn.common.domain.system.SchedulerProvider

/**
 * @author CodeRedWolf. Date 11.10.2019.
 */
class WordSetUserViewModel(
    schedulerProvider: SchedulerProvider
) : Store<Action, ViewState, Effect, Unit>(
    initialState = ViewState(),
    reducer = ReducerImpl(),
    navigator = NavigatorImpl(),
    schedulerProvider = schedulerProvider,
    middleWare = MiddleWareImpl()
) {

    override fun onNavigationEvent(event: Unit) = Unit

    private class ReducerImpl : Reducer<ViewState, Effect> {

        override fun reduce(state: ViewState, effect: Effect): ViewState {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    private class NavigatorImpl : Navigator<ViewState, Effect, Unit> {

        override fun handle(state: ViewState, effect: Effect): Unit? {
            return Unit
        }
    }

    private class MiddleWareImpl : MiddleWare<Action, ViewState, Effect> {

        override fun bind(action: Action, state: ViewState): Observable<Effect> {
           when(action){

           }
        }
    }

}





sealed class Action {

}

sealed class Effect {

}

class ViewState(
    val name: String = "",
    val age: Int = 0
)







