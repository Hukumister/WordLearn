package ru.coderedwolf.mvi.core

import io.reactivex.Observable

/**
 * @author CodeRedWolf. Date 13.10.2019.
 */
interface MiddleWare<Action, State, Effect> {

    fun handle(action: Action, state: State): Observable<Effect>
}
