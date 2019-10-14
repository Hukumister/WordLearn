package ru.coderedwolf.mvi.core

import io.reactivex.Observable

/**
 * @author CodeRedWolf. Date 14.10.2019.
 */
interface Bootstrapper<Action> {

    fun invoke(): Observable<Action>
}