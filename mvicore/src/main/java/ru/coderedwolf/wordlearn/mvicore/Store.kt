package ru.coderedwolf.wordlearn.mvicore

interface Store<Action, State> {

    fun bindView(mviView: MviView<Action, State>)
    fun unbindView()
}