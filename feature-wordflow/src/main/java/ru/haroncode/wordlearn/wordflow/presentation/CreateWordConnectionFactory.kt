package ru.haroncode.wordlearn.wordflow.presentation

import com.haroncode.gemini.android.connection.DelegateConnectionRuleFactory
import com.haroncode.gemini.connection.ConnectionRule
import com.haroncode.gemini.dsl.connectTo
import com.haroncode.gemini.dsl.connectionFactory
import com.haroncode.gemini.dsl.observeOn
import javax.inject.Inject
import ru.haroncode.wordlearn.common.di.PerFragment
import ru.haroncode.wordlearn.common.domain.system.SchedulerProvider
import ru.haroncode.wordlearn.wordflow.ui.CreateWordFragment

@PerFragment
class CreateWordConnectionFactory @Inject constructor(
    private val createWordStore: CreateWordStore,
    private val schedulerProvider: SchedulerProvider
) : DelegateConnectionRuleFactory<CreateWordFragment>() {

    override val connectionRuleFactory: ConnectionRule.Factory<CreateWordFragment> = connectionFactory { view ->
        rule { view connectTo createWordStore }
        rule { createWordStore connectTo view observeOn schedulerProvider.mainThread }
        autoDispose { createWordStore }
    }
}
