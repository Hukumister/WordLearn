package ru.coderedwolf.mvi.core.elements

import io.reactivex.Flowable

/**
 * @author CodeRedWolf. Date 13.10.2019.
 */
typealias Middleware<Action, State, Effect> = (action: Action, state: State) -> Flowable<Effect>