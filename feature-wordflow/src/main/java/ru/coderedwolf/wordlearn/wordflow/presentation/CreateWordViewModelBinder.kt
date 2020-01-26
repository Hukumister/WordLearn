package ru.coderedwolf.wordlearn.wordflow.presentation

import ru.coderedwolf.mvi.elements.Navigator
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

}