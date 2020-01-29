package ru.coderedwolf.mvi.core.elements

import io.reactivex.Flowable

/**
 * @author HaronCode.
 */
typealias Middleware<Action, State, Effect> = (action: Action, state: State) -> Flowable<Effect>