package ru.coderedwolf.mvi.core.elements

import io.reactivex.Observable

/**
 * @author CodeRedWolf. Date 14.10.2019.
 */
typealias Bootstrapper<Action> = () -> Observable<Action>