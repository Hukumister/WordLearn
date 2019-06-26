package ru.coderedwolf.wordlearn.ui.learn.word

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.Direction
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import kotlinx.android.synthetic.main.fragment_learn_words.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.model.learn.LearnWord
import ru.coderedwolf.wordlearn.presentation.learn.word.LearnWordsPresenter
import ru.coderedwolf.wordlearn.presentation.learn.word.LearnWordsView
import ru.coderedwolf.wordlearn.ui.base.BaseFragment
import ru.coderedwolf.wordlearn.ui.global.CardStackListenerSimple
import timber.log.Timber
import kotlin.random.Random

/**
 * @author CodeRedWolf. Date 22.06.2019.
 */
class LearnWordsFragment : BaseFragment(),
        LearnWordsView,
        CardStackListenerSimple {

    companion object {

        private const val VISIBLE_COUNT = 2
        private const val MAX_DEGREE = 35f
        private const val SWIPE_THRESHOLD = 0.5f

        private const val LOADING_MORE_THRESHOLD = 5
    }

    override val layoutRes: Int
        get() = R.layout.fragment_learn_words

    @InjectPresenter
    lateinit var presenter: LearnWordsPresenter

    @ProvidePresenter
    fun providePresenter(): LearnWordsPresenter =
            scope.getInstance(LearnWordsPresenter::class.java)

    private val wordAdapter = GroupAdapter<ViewHolder>()
    private lateinit var manager: CardStackLayoutManager

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        manager = CardStackLayoutManager(requireContext(), this)
        toolbar.setNavigationOnClickListener { presenter.onBackPressed() }

        initCardStackView()
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
        wordAdapter.addAll(learnList.map { LearnWordsItem(it) })
    }

    override fun add(learnWord: LearnWord) {
        val itemCount = wordAdapter.itemCount
        val index = Random.Default.nextInt(0, itemCount)
        wordAdapter.add(index, LearnWordsItem(learnWord))
    }

    override fun showTitle(title: String) {
        toolbar.title = title
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
}