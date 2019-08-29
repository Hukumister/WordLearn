package ru.coderedwolf.wordlearn.common.domain.system

import io.reactivex.Scheduler

/**
 * @author CodeRedWolf. Date 21.08.2019.
 */
interface SchedulerProvider {

    val single: Scheduler

    val io: Scheduler

    val computation: Scheduler

    val mainThread: Scheduler

}