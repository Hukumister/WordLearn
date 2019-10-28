package ru.coderedwolf.mvi.core

import io.reactivex.Observable

/**
 * @author CodeRedWolf. Date 13.10.2019.
 */
typealias Middleware<Action, State, Effect> = (action: Action, state: State) -> Observable<Effect>