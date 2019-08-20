package ru.coderedwolf.wordlearn.ui.learn.phrases

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.Direction
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import kotlinx.android.synthetic.main.fragment_learn_phrases.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.model.learn.LearnPhrase
import ru.coderedwolf.wordlearn.presentation.learn.phrases.LearnPhrasesPresenter
import ru.coderedwolf.wordlearn.presentation.learn.phrases.LearnPhrasesView
import ru.coderedwolf.wordlearn.ui.base.BaseFragment
import ru.coderedwolf.wordlearn.ui.global.CardStackListenerSimple
import ru.coderedwolf.wordlearn.ui.learn.LearnLoadingItem
import timber.log.Timber

/**
 * @author CodeRedWolf. Date 14.06.2019.
 */
class LearnPhrasesFragment : BaseFragment(),
        LearnPhrasesView,
        CardStackListenerSimple {

    override val layoutRes: Int
        get() = R.layout.fragment_learn_phrases

    companion object {

        private const val VISIBLE_COUNT = 2
        private const val MAX_DEGREE = 35f
        private const val SWIPE_THRESHOLD = 0.5f
    }

    @InjectPresenter
    lateinit var presenter: LearnPhrasesPresenter

    @ProvidePresenter
    fun providePresenter(): LearnPhrasesPresenter =
            scope.getInstance(LearnPhrasesPresenter::class.java)

    private lateinit var manager: CardStackLayoutManager

    private val mainSection = Section()
    private val phrasesAdapter = GroupAdapter<ViewHolder>().apply {
        add(mainSection)
    }

    private var isCanSwipe: Boolean = true
        set(value) {
            manager.setCanScrollHorizontal(value)
            field = value
        }

    override fun onResume() {
        super.onResume()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onStop() {
        super.onStop()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        manager = CardStackLayoutManager(requireContext(), this)
        toolbar.setNavigationOnClickListener { presenter.onBackPressed() }
        initCardStackView()
    }

    override fun addLearnList(list: List<LearnPhrase>) {
        mainSection.addAll(list.map { LearnPhraseItem(it) })
    }

    override fun onBackPressed() = presenter.onBackPressed()

    override fun showListLoading(show: Boolean) {
        when {
            show && mainSection.isEmpty() -> mainSection.add(LearnLoadingItem())
            show && !mainSection.isFirstLoading() -> mainSection.add(0, LearnLoadingItem())
            !show && mainSection.isFirstLoading() -> mainSection.remove(LearnLoadingItem())
        }
        isCanSwipe = !show
    }

    override fun showCategoryName(categoryName: String) {
        toolbar.subtitle = categoryName
    }


    override fun onCardAppeared(view: View?, position: Int) {
        Timber.d("onCardAppeared")
        mainSection.getPhrase(manager.topPosition)?.let { presenter.onCardAppeared(it) }
    }

    override fun onCardSwiped(direction: Direction) {
        Timber.d("Swipe phrase with position %s", manager.topPosition - 1)
        mainSection.getPhrase(manager.topPosition - 1)?.let { presenter.onNext(it) }
        mainSection.remove(mainSection.getItem(manager.topPosition - 1))
        checkForEmpty()
    }

    override fun showInfoCard() {

    }

    private fun checkForEmpty() {
        when {
            mainSection.isEmpty() -> presenter.onPhrasesGone()
            mainSection.isFirstLoading() && mainSection.itemCount == 1 -> presenter.onPhrasesGone()
        }
    }

    private fun Section.getPhrase(position: Int): LearnPhrase? {
        if (position >= itemCount) {
            return null
        }
        return (getItem(position) as? LearnPhraseItem)?.learnPhrase
    }

    private fun Section.isEmpty(): Boolean = itemCount == 0

    private fun Section.isFirstLoading(): Boolean = if (itemCount > 0) {
        getItem(0) is LearnLoadingItem
    } else {
        false
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
            adapter = phrasesAdapter
            itemAnimator = SlideInDownAnimator().apply {
                addDuration = 400
                removeDuration = 50
            }
        }
    }
}