package ru.coderedwolf.mvi.core.elements

import io.reactivex.Observable

/**
 * @author HaronCode.
 */
typealias Bootstrapper<Action> = () -> Observable<Action>