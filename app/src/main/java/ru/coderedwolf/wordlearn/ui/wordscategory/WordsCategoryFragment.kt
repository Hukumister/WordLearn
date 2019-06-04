package ru.coderedwolf.wordlearn.ui.wordscategory

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_words_category_list.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.jetbrains.anko.onClick
import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.extension.visibile
import ru.coderedwolf.wordlearn.model.Category
import ru.coderedwolf.wordlearn.presentation.wordcategory.WordsCategoryPresenter
import ru.coderedwolf.wordlearn.presentation.wordcategory.WordsCategoryView
import ru.coderedwolf.wordlearn.ui.base.BaseFragment

/**
 * @author CodeRedWolf. Date 01.05.2019.
 */
class WordsCategoryFragment : BaseFragment(),
        WordsCategoryView,
        CreateCategoryDialog.OnCreateCategory {

    override val layoutRes = R.layout.fragment_words_category_list

    @InjectPresenter
    lateinit var presenter: WordsCategoryPresenter

    @ProvidePresenter
    fun providePresenter(): WordsCategoryPresenter =
            scope.getInstance(WordsCategoryPresenter::class.java)

    private val categoryAdapter = GroupAdapter<ViewHolder>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCategoryFab.onClick { presenter.onClickAddCategory() }
        categoryList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = categoryAdapter
            setHasFixedSize(true)
        }
        categoryAdapter.setOnItemClickListener { item, _ ->
            if (item is WordsCategoryItem) {
                presenter.onClickCategory(item.category)
            }
        }
    }

    override fun onCreateCategory(name: String) = presenter.onCreateCategory(name)

    override fun addCategory(index: Int, category: Category) = categoryAdapter.add(index, WordsCategoryItem(category))

    override fun showCreateCategoryDialog() = CreateCategoryDialog.show(childFragmentManager)

    override fun addAllCategory(list: List<Category>) = categoryAdapter.addAll(list.map { mapToItem(it) })

    override fun updateCategoryList(list: List<Category>) = categoryAdapter.updateAsync(list.map { mapToItem(it) })

    override fun showLoading(show: Boolean) = progressBar.visibile(show)

    override fun onBackPressed() = presenter.onBackPressed()

    private fun mapToItem(it: Category): WordsCategoryItem {
        return WordsCategoryItem(it).apply {
            onChecked = { item, isChecked ->
                presenter.onCheckedCategory(item, isChecked)
            }
        }
    }
}