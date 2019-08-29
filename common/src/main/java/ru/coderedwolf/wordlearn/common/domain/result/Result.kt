package ru.coderedwolf.wordlearn.common.domain.result

import androidx.annotation.IntDef

/**
 * @author CodeRedWolf. Date 24.08.2019.
 */
sealed class Result<T> {

    companion object {

        const val STATE_LOADING = 1
        const val STATE_SUCCESS = 1 shl 1
        const val STATE_ERROR = 1 shl 2

        @IntDef(
            STATE_LOADING,
            STATE_SUCCESS,
            STATE_ERROR
        )
        annotation class State

    }

    @State
    abstract val state: Int

    object Loading : Result<Nothing>() {

        @State
        override val state: Int = STATE_LOADING
    }

    data class Data<T>(val value: T) : Result<T>() {

        @State
        override val state: Int = STATE_SUCCESS
    }

    data class Error(val throwable: Throwable) : Result<Nothing>() {

        @State
        override val state: Int = STATE_ERROR
    }

}