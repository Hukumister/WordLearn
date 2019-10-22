package ru.coderedwolf.wordlearn.mainflow.ui

import android.os.Bundle
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_phrase_topic_list.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.coderedwolf.wordlearn.common.ui.BaseFragment
import ru.coderedwolf.wordlearn.mainflow.R
import ru.coderedwolf.wordlearn.mainflow.presentation.PhraseTopicListPresenter
import ru.coderedwolf.wordlearn.mainflow.presentation.PhraseTopicView
import ru.coderedwolf.wordlearn.phrase.model.PhraseTopic
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 16.06.2019.
 */
class PhraseTopicListFragment : BaseFragment(), PhraseTopicView {
    override val layoutRes: Int = R.layout.fragment_phrase_topic_list

//    @Inject
    @InjectPresenter
    lateinit var presenter: PhraseTopicListPresenter

    @ProvidePresenter
    fun providePresenter(): PhraseTopicListPresenter = presenter

    private val phraseTopicAdapter = GroupAdapter<GroupieViewHolder>()

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

    override fun showAll(list: List<PhraseTopic>) =
        phraseTopicAdapter.update(list.map { PhraseTopicItem(it) })

    override fun onBackPressed() = presenter.onBackPressed()
}