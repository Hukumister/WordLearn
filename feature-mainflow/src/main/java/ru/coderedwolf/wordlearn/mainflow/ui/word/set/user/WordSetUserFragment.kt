package ru.coderedwolf.wordlearn.mainflow.ui.word.set.user

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_word_user_set.*
import ru.coderedwolf.mvi.core.Store
import ru.coderedwolf.wordlearn.common.domain.result.Product
import ru.coderedwolf.wordlearn.common.ui.MviFragment
import ru.coderedwolf.wordlearn.common.ui.adapter.DefaultClicker
import ru.coderedwolf.wordlearn.common.ui.adapter.ItemAsyncAdapter
import ru.coderedwolf.wordlearn.common.ui.adapter.delegate.ButtonAdapterDelegate
import ru.coderedwolf.wordlearn.common.util.unsafeLazy
import ru.coderedwolf.wordlearn.mainflow.R
import ru.coderedwolf.wordlearn.mainflow.presentation.set.WordSetUserViewModel
import ru.coderedwolf.wordlearn.mainflow.presentation.set.WordSetUserViewModel.Action
import ru.coderedwolf.wordlearn.mainflow.presentation.set.WordSetUserViewModel.ViewEvent
import ru.coderedwolf.wordlearn.mainflow.presentation.set.WordSetUserViewState
import ru.coderedwolf.wordlearn.mainflow.presentation.set.WordSetUserViewState.Item
import ru.coderedwolf.wordlearn.mainflow.ui.word.set.list.WordSetDelegate
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 11.10.2019.
 */
class WordSetUserFragment : MviFragment<Action, WordSetUserViewState, ViewEvent>(R.layout.fragment_word_user_set) {

    companion object {

        fun newInstance() = WordSetUserFragment()
    }

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private val wordSetUserViewModel: WordSetUserViewModel by viewModels {
        viewModelFactory
    }

    override val store: Store<Action, WordSetUserViewState, ViewEvent> = wordSetUserViewModel

    private val itemAdapter: ItemAsyncAdapter<Item> by unsafeLazy {
        ItemAsyncAdapter.Builder<Item>()
            .add(WordSetDelegate())
            .add(ButtonAdapterDelegate(), DefaultClicker(::onClickAddSet))
            .build()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = itemAdapter
        }

    }

    private fun onClickAddSet(item: Item) = postAction(Action.CreateNew)

    override fun render(state: WordSetUserViewState) = when (val items = state.items) {
        is Product.Data -> itemAdapter.updateItems(items.value)
        else -> Unit
    }

    override fun route(event: ViewEvent) = when (event) {
        is ViewEvent.CreateNewDialog -> Toast.makeText(requireContext(), "Dialog", Toast.LENGTH_SHORT).show()
    }

}