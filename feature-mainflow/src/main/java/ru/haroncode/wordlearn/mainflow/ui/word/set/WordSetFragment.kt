package ru.haroncode.wordlearn.mainflow.ui.word.set

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.haroncode.gemini.core.EventListener
import kotlinx.android.synthetic.main.fragment_word_set.*
import ru.haroncode.wordlearn.common.domain.model.Product
import ru.haroncode.wordlearn.common.ui.PublishFragment
import ru.haroncode.wordlearn.common.ui.adapter.ItemAsyncAdapter
import ru.haroncode.wordlearn.common.ui.adapter.delegate.TwoLineListItemAdapterDelegate
import ru.haroncode.wordlearn.common.util.unsafeLazy
import ru.haroncode.wordlearn.mainflow.R
import ru.haroncode.wordlearn.mainflow.presentation.set.WordSetUserStore.Action
import ru.haroncode.wordlearn.mainflow.presentation.set.WordSetUserStore.ViewEvent
import ru.haroncode.wordlearn.mainflow.presentation.set.WordSetViewState
import ru.haroncode.wordlearn.mainflow.presentation.set.WordSetViewState.Item

/**
 * @author HaronCode.
 */
class WordSetFragment : PublishFragment<Action, WordSetViewState>(R.layout.fragment_word_set),
    EventListener<ViewEvent> {

    companion object {

        fun newInstance() = WordSetFragment()
    }

    private val itemAdapter: ItemAsyncAdapter<Item> by unsafeLazy {
        ItemAsyncAdapter.Builder<Item>()
            .singleViewType(TwoLineListItemAdapterDelegate())
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

    override fun onViewStateChanged(state: WordSetViewState) = when (val items = state.items) {
        is Product.Data -> itemAdapter.updateItems(items.value)
        else -> Unit
    }

    override fun onEvent(event: ViewEvent) = when (event) {
        is ViewEvent.CreateNewDialog -> Toast.makeText(
            requireContext(),
            "Create dialog under construction",
            Toast.LENGTH_SHORT
        ).show()
    }
}
