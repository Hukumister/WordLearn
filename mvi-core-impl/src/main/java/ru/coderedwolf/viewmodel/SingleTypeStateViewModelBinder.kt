package ru.coderedwolf.viewmodel

import ru.coderedwolf.mvi.core.Store
import ru.coderedwolf.mvi.core.elements.Navigator

/**
 * @author HaronCode. Date 13.10.2019.
 */
abstract class SingleTypeStateViewModelBinder<Action : Any, State : Any, Event : Any>(
    store: Store<Action, State, Event>,
    navigator: Navigator<State, Event>
) : ViewModelBinder<Action, State, State, Event>(
    store = store,
    transformer = { state -> state },
    navigator = navigator
)