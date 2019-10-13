package ru.coderedwolf.wordlearn.mainflow.ui.word.set

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_word_user_set.*
import ru.coderedwolf.wordlearn.common.ui.BaseFragment
import ru.coderedwolf.wordlearn.common.ui.event.UiEvent
import ru.coderedwolf.wordlearn.common.ui.item.BaseAdapter
import ru.coderedwolf.wordlearn.common.util.autoCleared
import ru.coderedwolf.wordlearn.mainflow.R

/**
 * @author CodeRedWolf. Date 11.10.2019.
 */
class WordSetUserFragment : BaseFragment(),
    ObservableSource<UiEvent> {

    companion object {

        fun newInstance() = WordSetUserFragment()
    }

    override val layoutRes: Int = R.layout.fragment_word_user_set


    private val source = PublishSubject.create<UiEvent>()

    private var itemAdapter by autoCleared<BaseAdapter>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemAdapter = BaseAdapter()

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = itemAdapter
        }

    }

    override fun subscribe(observer: Observer<in UiEvent>) = source.subscribe(observer)

}