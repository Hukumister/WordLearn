package ru.coderedwolf.wordlearn.wordflow.ui

import android.os.Bundle
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.view.isVisible
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_word_list.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.coderedwolf.wordlearn.common.extension.onClick
import ru.coderedwolf.wordlearn.common.ui.BaseFragment
import ru.coderedwolf.wordlearn.word.model.WordPreview
import ru.coderedwolf.wordlearn.wordflow.R
import ru.coderedwolf.wordlearn.wordflow.presentation.WordListPresenter
import ru.coderedwolf.wordlearn.wordflow.presentation.WordListView
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 04.06.2019.
 */
class WordListFragment : BaseFragment(), WordListView {

    override val layoutRes = R.layout.fragment_word_list

    @Inject
    @InjectPresenter
    lateinit var presenter: WordListPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    private val wordPreviewAdapter = GroupAdapter<ViewHolder>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.setNavigationOnClickListener { presenter.onBackPressed() }
        toolbar.inflateMenu(R.menu.menu_check_box)
        toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.action_learn) {
                val checkBox = item.actionView
                    .findViewById<AppCompatCheckBox>(R.id.checkBox)
                    ?: return@setOnMenuItemClickListener false
                presenter.onClickActionLearn(checkBox.isChecked)
                true
            } else {
                false
            }
        }
        wordPreviewList.apply {
            adapter = wordPreviewAdapter
            setHasFixedSize(true)
        }
        addWordFab.onClick { presenter.onClickAddWord() }
    }

    override fun showLoading(show: Boolean) {

    }

    override fun showWords(list: List<WordPreview>) {
        placeHolder.isVisible = list.isEmpty()
        wordPreviewAdapter.update(list.map { WordPreviewItem(it) })
    }

    override fun showTitle(title: String) {
        toolbar.title = title
    }

    override fun onBackPressed() = presenter.onBackPressed()
}
