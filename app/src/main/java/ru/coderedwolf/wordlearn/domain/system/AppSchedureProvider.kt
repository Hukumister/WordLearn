package ru.coderedwolf.wordlearn.domain.system

/**
 * @author CodeRedWolf. Date 21.08.2019.
 */
private typealias RxScheduler = io.reactivex.schedulers.Schedulers

private typealias AndroidScheduler = io.reactivex.android.schedulers.AndroidSchedulers

class AppSchedureProvider : SchedulerProvider {

    override val single = RxScheduler.single()

    override val io = RxScheduler.io()

    override val computation = RxScheduler.computation()

    override val mainThread = AndroidScheduler.mainThread()

}