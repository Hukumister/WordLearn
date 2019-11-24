package ru.coderedwolf.mvi.core.impl

import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import ru.coderedwolf.mvi.core.MviView

/**
 * @author CodeRedWolf.
 */
class TestView(
    actionTestSubject: PublishSubject<TestAction>,
    private val testObserver: TestObserver<TestViewState>
) : MviView<TestAction, TestViewState, TestViewEvent> {

    override val actions: Observable<TestAction> = actionTestSubject.hide()

    override fun render(state: TestViewState) {
        testObserver.onNext(state)
    }

}