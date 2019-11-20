package ru.coderedwolf.wordlearn.mainflow.ui.word.set.user

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.dialog_fragment_create_word_set.*
import ru.coderedwolf.mvi.core.Store
import ru.coderedwolf.wordlearn.common.domain.result.Determinate
import ru.coderedwolf.wordlearn.common.domain.system.SchedulerProvider
import ru.coderedwolf.wordlearn.common.domain.validator.ResourceViolation
import ru.coderedwolf.wordlearn.common.ui.MviDialogFragment
import ru.coderedwolf.wordlearn.mainflow.R
import ru.coderedwolf.wordlearn.mainflow.presentation.set.create.WordSetCreateViewModel
import ru.coderedwolf.wordlearn.mainflow.presentation.set.create.WordSetCreateViewModel.Action
import ru.coderedwolf.wordlearn.mainflow.presentation.set.create.WordSetCreateViewState
import javax.inject.Inject

/**
 * @author CodeRedWolf.
 */
class WordSetCreateDialog : MviDialogFragment<Action, WordSetCreateViewState, Nothing>() {

    companion object {

        const val TAG = "WordSetCreateDialog"

        fun newInstance(): WordSetCreateDialog = WordSetCreateDialog()

    }

    override val layoutRes: Int = R.layout.dialog_fragment_create_word_set

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var schedulerProvider: SchedulerProvider

    private val wordSetCreateViewModel: WordSetCreateViewModel by viewModels {
        viewModelFactory
    }

    override val store: Store<Action, WordSetCreateViewState, Nothing> = wordSetCreateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        check(showsDialog) { "Unsupported showing as fragment" }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RxTextView.textChangeEvents(wordSetTitle)
                .skipInitialValue()
                .observeOn(schedulerProvider.computation)
                .map { event -> Action.ChangeName(event.text()) }
                .autoDisposable()
                .subscribe(::postAction)
    }

    override fun render(state: WordSetCreateViewState) {

        progressBar.isVisible = state.saveDeterminate is Determinate.Loading
        wordSetLayout.error = state.wordSetTitle
                ?.verifiable
                ?.let { verifiable -> verifiable as? ResourceViolation }
                ?.res?.resString()
    }

}