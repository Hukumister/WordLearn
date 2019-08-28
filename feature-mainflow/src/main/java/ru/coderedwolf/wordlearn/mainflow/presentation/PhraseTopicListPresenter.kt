package ru.coderedwolf.wordlearn.mainflow.presentation

import moxy.InjectViewState
import ru.coderedwolf.wordlearn.common.di.PerFragment
import ru.coderedwolf.wordlearn.common.presentation.BasePresenter
import ru.coderedwolf.wordlearn.common.presentation.FlowRouter
import ru.coderedwolf.wordlearn.mainflow.domain.interactor.PhraseTopicInteractor
import ru.coderedwolf.wordlearn.phrase.model.PhraseTopic
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 16.06.2019.
 */
@PerFragment
@InjectViewState
class PhraseTopicListPresenter @Inject constructor(
    private val flowRouter: FlowRouter,
    private val phraseTopicInteractor: PhraseTopicInteractor,
    private val flows: PhraseTopicReachableFlows
) : BasePresenter<PhraseTopicView>() {

    private val topicList = mutableListOf<PhraseTopic>()

    override fun onFirstViewAttach() = launchUI {
        val list = phraseTopicInteractor.findAll()
        topicList.addAll(list)
        viewState.showAll(topicList)
    }

    override fun onViewAttach(view: PhraseTopicView?) = viewState.showAll(topicList)

    override fun onBackPressed() = flowRouter.finishFlow()

    fun onClickTopic(phraseTopic: PhraseTopic) = flowRouter.startFlow(flows.phraseFlow(phraseTopic.id!!))
}