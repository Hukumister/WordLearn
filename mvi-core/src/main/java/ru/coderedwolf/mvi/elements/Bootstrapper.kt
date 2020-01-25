package ru.coderedwolf.mvi.elements

import io.reactivex.Observable

/**
 * @author HaronCode.
 */
typealias Bootstrapper<Action> = () -> Observable<Action>