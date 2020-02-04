package ru.coderedwolf.wordlearn.learnwordsflow.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.Direction
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import kotlinx.android.synthetic.main.fragment_learn_words.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.coderedwolf.wordlearn.common.ui.BaseFragment
import ru.coderedwolf.wordlearn.common.ui.CardStackListenerSimple
import ru.coderedwolf.wordlearn.learnwordsflow.R
import ru.coderedwolf.wordlearn.learnwordsflow.model.LearnWord
import ru.coderedwolf.wordlearn.learnwordsflow.presentation.LearnWordsPresenter
import ru.coderedwolf.wordlearn.learnwordsflow.presentation.LearnWordsView
import timber.log.Timber
import javax.inject.Inject

/**
 * @author HaronCode. Date 22.06.2019.
 */
class LearnWordsFlowFragment : BaseFragment(R.layout.fragment_learn_words),
    LearnWordsView,
    CardStackListenerSimple {

    companion object {

        private const val VISIBLE_COUNT = 2
        private const val MAX_DEGREE = 35f
        private const val SWIPE_THRESHOLD = 0.5f

        private const val LOADING_MORE_THRESHOLD = 5
    }

//    @Inject
//    @InjectPresenter
    lateinit var presenter: LearnWordsPresenter

//    @ProvidePresenter
    fun providePresenter() = presenter

    private val wordAdapter = GroupAdapter<GroupieViewHolder>()
    private lateinit var manager: CardStackLayoutManager

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        manager = CardStackLayoutManager(requireContext(), this)
        toolbar.setNavigationOnClickListener { presenter.onBackPressed() }

        initCardStackView()
    }

    override fun showLoading(show: Boolean) {
        progressBar.isVisible = show
    }

    override fun onResume() {
        super.onResume()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onStop() {
        super.onStop()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
    }

    override fun onCardAppeared(view: View?, position: Int) {
        Timber.d("onCardAppeared")
        val wordsItem = wordAdapter.getItem(manager.topPosition) as? LearnWordsItem
        wordsItem?.let { presenter.onWordAppear(it.learnWord) }
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        if (wordAdapter.itemCount <= LOADING_MORE_THRESHOLD) {
            presenter.onLoadMore()
        }
    }

    override fun onCardSwiped(direction: Direction) {
        Timber.d("Swipe phrase with position %s", manager.topPosition - 1)
        val wordsItem = wordAdapter
            .getItem(manager.topPosition - 1) as? LearnWordsItem ?: return

        when (direction) {
            Direction.Left -> wordsItem.let { presenter.onMissIt(it.learnWord) }
            Direction.Right -> wordsItem.let { presenter.onGotIt(it.learnWord) }
            else -> Timber.e("Unsupported direction on swipe")
        }
    }

    override fun addAll(learnList: List<LearnWord>) {
        wordAdapter.addAll(learnList.map { word -> LearnWordsItem(word) })
    }

    override fun add(learnWord: LearnWord) {
        wordAdapter.add(LearnWordsItem(learnWord))
    }

    override fun showTitle(title: String) {
        toolbar.title = title
    }

    override fun showSubTitle(subTitle: String) {
        toolbar.subtitle = subTitle
    }

    private fun initCardStackView() {
        manager.apply {
            setVisibleCount(VISIBLE_COUNT)
            setMaxDegree(MAX_DEGREE)
            setSwipeThreshold(SWIPE_THRESHOLD)
            setCanScrollVertical(false)
        }
        cardStackView.apply {
            layoutManager = manager
            adapter = wordAdapter
            itemAnimator = SlideInDownAnimator().apply {
                addDuration = 400
                removeDuration = 50
            }
        }
    }

    override fun onBackPressed() = presenter.onBackPressed()
}