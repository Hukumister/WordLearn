package ru.haroncode.wordlearn.mainflow.ui.word.set.user

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.haroncode.gemini.core.EventListener
import kotlinx.android.synthetic.main.fragment_word_user_set.*
import ru.haroncode.wordlearn.common.domain.result.Product
import ru.haroncode.wordlearn.common.ui.PublishFragment
import ru.haroncode.wordlearn.common.ui.adapter.DefaultItemClicker
import ru.haroncode.wordlearn.common.ui.adapter.ItemAsyncAdapter
import ru.haroncode.wordlearn.common.ui.adapter.SealedClassViewTypeSelector
import ru.haroncode.wordlearn.common.ui.adapter.delegate.ButtonAdapterDelegate
import ru.haroncode.wordlearn.common.util.unsafeLazy
import ru.haroncode.wordlearn.mainflow.R
import ru.haroncode.wordlearn.mainflow.presentation.set.WordSetUserStore.Action
import ru.haroncode.wordlearn.mainflow.presentation.set.WordSetUserStore.ViewEvent
import ru.haroncode.wordlearn.mainflow.presentation.set.WordSetUserViewState
import ru.haroncode.wordlearn.mainflow.presentation.set.WordSetUserViewState.Item
import ru.haroncode.wordlearn.mainflow.presentation.set.WordSetUserViewState.Item.AddWordSetItem
import ru.haroncode.wordlearn.mainflow.presentation.set.WordSetUserViewState.Item.WordSetItem
import ru.haroncode.wordlearn.mainflow.ui.word.set.list.WordSetDelegate

/**
 * @author HaronCode.
 */
class WordSetUserFragment : PublishFragment<Action, WordSetUserViewState>(R.layout.fragment_word_user_set),
    EventListener<ViewEvent> {

    companion object {

        fun newInstance() = WordSetUserFragment()
    }

    private val itemAdapter: ItemAsyncAdapter<Item> by unsafeLazy {
        val viewTypeSelector = SealedClassViewTypeSelector.of(WordSetItem::class, AddWordSetItem::class)
        ItemAsyncAdapter.Builder<Item>()
            .withViewTypeSelector(viewTypeSelector::viewTypeFor)
            .add(WordSetItem::class, WordSetDelegate())
            .add(AddWordSetItem::class, ButtonAdapterDelegate(), DefaultItemClicker(::onClickAddSet))
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

    override fun onViewStateChanged(state: WordSetUserViewState) = when (val items = state.items) {
        is Product.Data -> itemAdapter.updateItems(items.value)
        else -> Unit
    }

    override fun onEvent(event: ViewEvent) = when (event) {
        is ViewEvent.CreateNewDialog -> Toast.makeText(requireContext(), "Dialog", Toast.LENGTH_SHORT).show()
    }
}
