package ru.coderedwolf.wordlearn.ui.learn

import android.os.Bundle
import android.view.View
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.Direction
import kotlinx.android.synthetic.main.fragment_learn_phrases.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.jetbrains.anko.toast
import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.model.learn.LearnPhrase
import ru.coderedwolf.wordlearn.presentation.learn.LearnPhrasesPresenter
import ru.coderedwolf.wordlearn.presentation.learn.LearnPhrasesView
import ru.coderedwolf.wordlearn.ui.base.BaseFragment
import ru.coderedwolf.wordlearn.ui.global.CardStackListenerSimple
import timber.log.Timber

/**
 * @author CodeRedWolf. Date 14.06.2019.
 */
class LearnPhrasesFragment : BaseFragment(),
        LearnPhrasesView,
        CardStackListenerSimple {


    override val layoutRes: Int
        get() = R.layout.fragment_learn_phrases

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
        Timber.d("Swipe phrase with position %s and direction %s", manager.topPosition - 1, direction)
        when (direction) {
            Direction.Left -> mainSection.getPhrase(manager.topPosition - 1)?.let { presenter.onMissedPhrase(it) }
            Direction.Right -> mainSection.getPhrase(manager.topPosition - 1)?.let { presenter.onGotPhrase(it) }
            else -> Timber.e("Unsupported swipe direction")
        }
        mainSection.remove(mainSection.getItem(manager.topPosition - 1))
        checkForEmpty()
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

    private fun Section.isFirstLoading(): Boolean = getItem(0) is LearnLoadingItem

    private fun initCardStackView() {
        manager.apply {
            setVisibleCount(1)
            setSwipeThreshold(0.5f)
            setCanScrollVertical(false)
        }
        cardStackView.apply {
            layoutManager = manager
            adapter = phrasesAdapter
            itemAnimator = null
        }
    }
}