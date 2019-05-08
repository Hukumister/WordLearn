package ru.coderedwolf.wordlearn.ui.wordscategory

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_words_category_list.*
import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.extension.gone
import ru.coderedwolf.wordlearn.model.Category
import ru.coderedwolf.wordlearn.presentation.WordsCategoryPresenter
import ru.coderedwolf.wordlearn.ui.base.BaseFragment
import toothpick.Toothpick

/**
 * @author CodeRedWolf. Date 01.05.2019.
 */
class WordsCategoryFragment : BaseFragment(), WordsCategoryView {

    override val layoutRes = R.layout.fragment_words_category_list

    @InjectPresenter
    lateinit var presenter: WordsCategoryPresenter

    @ProvidePresenter
    fun providePresenter(): WordsCategoryPresenter =
            scope.getInstance(WordsCategoryPresenter::class.java)

    private val categoryAdapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addCategoryFab.onCli
        categoryAdapter.setOnItemClickListener { item, _ ->
            if (item is WordsCategoryItem) {
                presenter.onClickCategory(item.category)
            }
        }
    }

    override fun showCategory(category: Category, index: Int) = categoryAdapter.add(index, WordsCategoryItem(category))

    override fun showCreateCategoryDialog() = CreateCategoryDialog.show(childFragmentManager)

    override fun showCategoryList(list: List<Category>) = categoryAdapter.addAll(list.map { WordsCategoryItem(it) })

    override fun showLoading(show: Boolean) {
        progressBar.gone(!show)
    }
}