package ru.coderedwolf.wordlearn.ui.word

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_word_list.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.jetbrains.anko.onClick
import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.extension.visibile
import ru.coderedwolf.wordlearn.extension.visibleOrGone
import ru.coderedwolf.wordlearn.model.WordPreview
import ru.coderedwolf.wordlearn.presentation.word.WordListPresenter
import ru.coderedwolf.wordlearn.presentation.word.WordListView
import ru.coderedwolf.wordlearn.ui.base.BaseFragment

/**
 * @author CodeRedWolf. Date 04.06.2019.
 */
class WordListFragment : BaseFragment(), WordListView {

    override val layoutRes: Int
        get() = R.layout.fragment_word_list

    companion object {

        fun newInstance(): Fragment = WordListFragment()
    }

    @InjectPresenter
    lateinit var presenter: WordListPresenter

    @ProvidePresenter
    fun providePresenter(): WordListPresenter =
            scope.getInstance(WordListPresenter::class.java)

    private val wordPreviewAdapter = GroupAdapter<ViewHolder>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.setNavigationOnClickListener { presenter.onBackPressed() }
        wordPreviewList.apply {
            adapter = wordPreviewAdapter
            setHasFixedSize(true)
        }
        addWordFab.onClick { presenter.onClickAddWord() }
    }

    override fun showLoading(show: Boolean) = progressBar.visibleOrGone(show)

    override fun showWords(list: List<WordPreview>) = wordPreviewAdapter
            .update(list.map { WordPreviewItem(it) })

    override fun showEmptyPlaceHolder(show: Boolean) = placeHolder.visibile(show)

    override fun showTitle(title: String) {
        toolbar.title = title
    }

    override fun onBackPressed() = presenter.onBackPressed()
}
