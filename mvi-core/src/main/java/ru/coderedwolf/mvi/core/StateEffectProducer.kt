package ru.coderedwolf.mvi.core

import org.reactivestreams.Publisher

interface StateEffectProducer<State : Any, Effect : Any> {

    val stateEffectSource: Publisher<Pair<State, Effect>>

}