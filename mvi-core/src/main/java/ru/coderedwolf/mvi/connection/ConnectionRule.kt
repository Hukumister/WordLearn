package ru.coderedwolf.mvi.connection

import io.reactivex.disposables.Disposable

interface ConnectionRule {

    fun connect(): Disposable

}