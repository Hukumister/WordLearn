package ru.coderedwolf.wordlearn.mainflow.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_words_category_list.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.coderedwolf.wordlearn.common.extension.onClick
import ru.coderedwolf.wordlearn.common.ui.BaseFragment
import ru.coderedwolf.wordlearn.common.ui.InputTextDialogFragment
import ru.coderedwolf.wordlearn.mainflow.R
import ru.coderedwolf.wordlearn.mainflow.presentation.WordsCategoryPresenter
import ru.coderedwolf.wordlearn.mainflow.presentation.WordsCategoryView
import ru.coderedwolf.wordlearn.wordscategory.model.WordCategory
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 01.05.2019.
 */
class WordsCategoryFragment : BaseFragment(),
    WordsCategoryView,
    InputTextDialogFragment.OnClickListener {

    override val layoutRes = R.layout.fragment_words_category_list

    @Inject
    @InjectPresenter
    lateinit var presenter: WordsCategoryPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

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
                presenter.onClickCategory(item.wordCategory)
            }
        }
    }

    override fun inputDialogTextAccepted(tag: String, text: String) = presenter.onCreateCategory(text)

    override fun addCategory(index: Int, wordCategory: WordCategory) = categoryAdapter.add(
        index,
        WordsCategoryItem(wordCategory)
    )

    override fun showCreateCategoryDialog() = InputTextDialogFragment.create(
        title = getString(R.string.create_category_title)
    ).show(childFragmentManager, "create_category")

    override fun addAllCategory(list: List<WordCategory>) = categoryAdapter.addAll(list.map { mapToItem(it) })

    override fun updateCategoryList(list: List<WordCategory>) = categoryAdapter.updateAsync(list.map { mapToItem(it) })

    override fun showLoading(show: Boolean) {
        progressBar.isVisible = show
    }

    override fun onBackPressed() = presenter.onBackPressed()

    private fun mapToItem(it: WordCategory): WordsCategoryItem = WordsCategoryItem(it)
}