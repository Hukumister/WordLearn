package ru.coderedwolf.viewmodel

import io.reactivex.Scheduler
import ru.coderedwolf.mvi.core.Store
import ru.coderedwolf.mvi.elements.Navigator

/**
 * @author HaronCode. Date 13.10.2019.
 */
abstract class SingleTypeStateViewModelBinder<Action : Any, State : Any, Event : Any>(
    store: Store<Action, State, Event>,
    scheduler: Scheduler,
    navigator: Navigator<State, Event>
) : ViewModelBinder<Action, State, State, Event>(
    store = store,
    scheduler = scheduler,
    transformer = { state -> state },
    navigator = navigator
)