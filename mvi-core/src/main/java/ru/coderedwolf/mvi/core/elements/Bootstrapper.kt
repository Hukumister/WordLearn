package ru.coderedwolf.mvi.core.elements

import io.reactivex.Observable

/**
 * @author HaronCode. Date 14.10.2019.
 */
typealias Bootstrapper<Action> = () -> Observable<Action>