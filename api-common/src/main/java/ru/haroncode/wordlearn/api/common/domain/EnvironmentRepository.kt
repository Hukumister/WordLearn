package ru.haroncode.wordlearn.api.common.domain

import io.reactivex.Single

interface EnvironmentRepository {

    fun isFirstLaunch(): Single<Boolean>
}
