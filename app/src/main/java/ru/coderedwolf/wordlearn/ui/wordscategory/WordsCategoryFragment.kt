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
import ru.coderedwolf.wordlearn.extension.visible
import ru.coderedwolf.wordlearn.model.word.WordCategory
import ru.coderedwolf.wordlearn.presentation.wordcategory.WordsCategoryPresenter
import ru.coderedwolf.wordlearn.presentation.wordcategory.WordsCategoryView
import ru.coderedwolf.wordlearn.ui.base.BaseFragment
import ru.coderedwolf.wordlearn.ui.global.InputTextDialogFragment

/**
 * @author CodeRedWolf. Date 01.05.2019.
 */
class WordsCategoryFragment : BaseFragment(),
        WordsCategoryView,
        InputTextDialogFragment.OnClickListener {

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
                presenter.onClickCategory(item.wordCategory)
            }
        }
    }

    override fun inputDialogTextAccepted(tag: String, text: String) = presenter.onCreateCategory(text)

    override fun addCategory(index: Int, wordCategory: WordCategory) = categoryAdapter.add(index, WordsCategoryItem(wordCategory))

    override fun showCreateCategoryDialog() = InputTextDialogFragment.create(
            title = getString(R.string.create_category_title)
    ).show(childFragmentManager, "create_category")

    override fun addAllCategory(list: List<WordCategory>) = categoryAdapter.addAll(list.map { mapToItem(it) })

    override fun updateCategoryList(list: List<WordCategory>) = categoryAdapter.updateAsync(list.map { mapToItem(it) })

    override fun showLoading(show: Boolean) = progressBar.visible(show)

    override fun onBackPressed() = presenter.onBackPressed()

    private fun mapToItem(it: WordCategory): WordsCategoryItem = WordsCategoryItem(it)
}