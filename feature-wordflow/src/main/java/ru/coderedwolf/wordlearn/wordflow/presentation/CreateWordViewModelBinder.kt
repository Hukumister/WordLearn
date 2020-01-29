package ru.coderedwolf.wordlearn.wordflow.presentation

import io.reactivex.functions.Consumer
import ru.coderedwolf.mvi.binder.ConnectionViewBinder
import ru.coderedwolf.mvi.binder.dsl.binding
import ru.coderedwolf.mvi.binder.dsl.noneTransformer
import ru.coderedwolf.mvi.binder.dsl.with
import ru.coderedwolf.mvi.core.elements.Navigator
import ru.coderedwolf.viewmodel.SingleTypeStateViewModelBinder
import ru.coderedwolf.wordlearn.common.domain.result.Determinate
import ru.coderedwolf.wordlearn.common.domain.system.SchedulerProvider
import ru.coderedwolf.wordlearn.common.presentation.FlowRouter
import ru.coderedwolf.wordlearn.wordflow.presentation.CreateWordStore.Action
import ru.coderedwolf.wordlearn.wordflow.presentation.CreateWordStore.Event

class CreateWordViewModelBinder(
    flowRouter: FlowRouter,
    schedulerProvider: SchedulerProvider,
    store: CreateWordStore
) : SingleTypeStateViewModelBinder<Action, CreateWordViewState, Event>(
    store = store,
    navigator = CreateWordNavigator(flowRouter),
    scheduler = schedulerProvider.mainThread
) {

    override val childBinding: ConnectionViewBinder<Action, CreateWordViewState> = binding { storeView ->
        connection { storeView to FakeAnalyticConsumer() with noneTransformer() }
    }

    class CreateWordNavigator(
        private val router: FlowRouter
    ) : Navigator<CreateWordViewState, Event> {

        override fun invoke(state: CreateWordViewState, event: Event) {
            when (event) {
                is Event.SaveResult -> {
                    if (event.determinate is Determinate.Completed) {
                        router.finishFlow()
                    }
                }
            }
        }

    }


    class FakeAnalyticConsumer : Consumer<Action> {

        override fun accept(t: Action) {
            //log event
        }

    }

}