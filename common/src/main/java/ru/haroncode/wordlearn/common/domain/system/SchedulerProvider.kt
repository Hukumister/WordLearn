package ru.haroncode.wordlearn.common.domain.system

import io.reactivex.Scheduler

/**
 * @author HaronCode. Date 21.08.2019.
 */
interface SchedulerProvider {

    val single: Scheduler

    val io: Scheduler

    val computation: Scheduler

    val mainThread: Scheduler
}
