package ru.coderedwolf.wordlearn.ui.phrase

import android.os.Bundle
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_phrase_topic_list.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.model.phrase.PhraseTopic
import ru.coderedwolf.wordlearn.presentation.phrase.PhraseTopicListPresenter
import ru.coderedwolf.wordlearn.presentation.phrase.PhraseTopicView
import ru.coderedwolf.wordlearn.ui.base.BaseFragment

/**
 * @author CodeRedWolf. Date 16.06.2019.
 */
class PhraseTopicListFragment : BaseFragment(), PhraseTopicView {

    override val layoutRes: Int = R.layout.fragment_phrase_topic_list

    @InjectPresenter
    lateinit var presenter: PhraseTopicListPresenter

    @ProvidePresenter
    fun providePresenter(): PhraseTopicListPresenter =
            scope.getInstance(PhraseTopicListPresenter::class.java)

    private val phraseTopicAdapter = GroupAdapter<ViewHolder>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        topicList.apply {
            adapter = phraseTopicAdapter
            setHasFixedSize(true)
        }
        phraseTopicAdapter.setOnItemClickListener { item, _ ->
            if (item is PhraseTopicItem) {
                presenter.onClickTopic(item.phraseTopic)
            }
        }
    }

    override fun showAll(list: List<PhraseTopic>) = phraseTopicAdapter
            .update(list.map { mapToItem(it) })

    private fun mapToItem(it: PhraseTopic) = PhraseTopicItem(it) { topic, isChecked ->
        presenter.onChangeStudy(topic, isChecked)
    }
}