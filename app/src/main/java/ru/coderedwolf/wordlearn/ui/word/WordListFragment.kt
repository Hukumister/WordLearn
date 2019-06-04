package ru.coderedwolf.wordlearn.ui.word

import androidx.fragment.app.Fragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.presentation.word.WordListPresenter
import ru.coderedwolf.wordlearn.ui.base.BaseFragment

/**
 * @author CodeRedWolf. Date 04.06.2019.
 */
class WordListFragment : BaseFragment() {

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



}
