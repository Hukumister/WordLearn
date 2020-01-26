package ru.coderedwolf.wordlearn.wordflow.ui

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.fragment_create_word.*
import ru.coderedwolf.fragment.MviFragment
import ru.coderedwolf.mvi.core.EventListener
import ru.coderedwolf.viewmodel.ViewModelBinder
import ru.coderedwolf.wordlearn.common.domain.result.Determinate
import ru.coderedwolf.wordlearn.common.domain.system.SchedulerProvider
import ru.coderedwolf.wordlearn.common.domain.validator.ResourceViolation
import ru.coderedwolf.wordlearn.common.extension.onClick
import ru.coderedwolf.wordlearn.common.presentation.FlowRouter
import ru.coderedwolf.wordlearn.common.ui.adapter.DefaultClicker
import ru.coderedwolf.wordlearn.common.ui.adapter.ItemAsyncAdapter
import ru.coderedwolf.wordlearn.common.util.ContextExtensionsHolder
import ru.coderedwolf.wordlearn.word.model.WordExample
import ru.coderedwolf.wordlearn.wordflow.R
import ru.coderedwolf.wordlearn.wordflow.presentation.CreateWordStore.Action
import ru.coderedwolf.wordlearn.wordflow.presentation.CreateWordStore.Event
import ru.coderedwolf.wordlearn.wordflow.presentation.CreateWordViewModelBinder
import ru.coderedwolf.wordlearn.wordflow.presentation.CreateWordViewState
import ru.coderedwolf.wordlearn.wordflow.presentation.CreateWordViewState.Item
import javax.inject.Inject

/**
 * @author HaronCode. Date 06.06.2019.
 */
class CreateWordFragment : MviFragment<Action, CreateWordViewState>(R.layout.fragment_create_word),
    ContextExtensionsHolder,
    EventListener<Event>,
    CreateWordExampleDialogFragment.OnCreateExampleListener {

    private lateinit var wordExampleAdapter: ItemAsyncAdapter<Item>

    //todo add correct init
    private lateinit var viewModel: CreateWordViewModelBinder

    @Inject lateinit var schedulerProvider: SchedulerProvider
    @Inject lateinit var router: FlowRouter
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override val extensionContext: Context = requireContext()

    override val viewModelBinder: ViewModelBinder<Action, *, CreateWordViewState, Event>
        get() = viewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.setNavigationOnClickListener { router.exit() }

        wordExampleAdapter = ItemAsyncAdapter.Builder<Item>()
            .add(WordExampleAdapterDelegates(), DefaultClicker { showDialogCreateExample() })
            .add(CreateWordAdapterDelegate(), DefaultClicker(::onClickRemoveExample))
            .build()

        examplesList.apply {
            itemAnimator = null
            adapter = wordExampleAdapter
        }

        saveButton.onClick { postAction(Action.SaveWord) }

        word.connect(Action::ChangeWord)
        transcription.connect(Action::ChangeTranscription)
        translation.connect(Action::ChangeTranslation)
        association.connect(Action::ChangeAssociation)

    }

    override fun onViewStateChanged(state: CreateWordViewState) {
        updateExampleList(state.exampleListItem)

        wordLayout.error = (state.word.verifiable as? ResourceViolation)?.res?.resString()
        translationLayout.error = (state.translation.verifiable as? ResourceViolation)?.res?.resString()
        saveButton.isEnabled = state.word.isValid
                && state.translation.isValid
                && state.determinate !is Determinate.Loading
    }

    private fun onClickRemoveExample(item: Item.WordExampleItem) = item.wordExample
        .let(Action::RemoveExample)
        .let(::postAction)

    private fun updateExampleList(list: List<Item>) = wordExampleAdapter.updateItems(list)

    override fun onCreateWordExample(wordExample: WordExample) = wordExample
        .let(Action::AddExample)
        .let(::postAction)

    private fun showDialogCreateExample() = CreateWordExampleDialogFragment.instance()
        .show(childFragmentManager, CreateWordExampleDialogFragment.TAG)

    override fun onEvent(event: Event) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        examplesList.adapter = null
    }

    //to do add dispose
    private fun EditText.connect(
        action: (CharSequence) -> Action
    ) = RxTextView.textChangeEvents(this)
        .skipInitialValue()
        .observeOn(schedulerProvider.computation)
        .map { event -> action(event.text()) }
        .subscribe(::postAction)

}