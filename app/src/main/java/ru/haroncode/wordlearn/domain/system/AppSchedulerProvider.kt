package ru.haroncode.wordlearn.domain.system

import ru.haroncode.wordlearn.common.domain.system.SchedulerProvider
import javax.inject.Inject

/**
 * @author HaronCode. Date 21.08.2019.
 */
private typealias RxScheduler = io.reactivex.schedulers.Schedulers

private typealias AndroidScheduler = io.reactivex.android.schedulers.AndroidSchedulers

class AppSchedulerProvider @Inject constructor() : SchedulerProvider {

    override val single = RxScheduler.single()
    override val io = RxScheduler.io()
    override val computation = RxScheduler.computation()
    override val mainThread = AndroidScheduler.mainThread()!!

}