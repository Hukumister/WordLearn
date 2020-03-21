package ru.haroncode.wordlearn.wordflow.ui

import android.os.Bundle
import android.widget.EditText
import com.haroncode.gemini.android.StoreViewConnector
import com.jakewharton.rxbinding2.widget.RxTextView
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_create_word.*
import ru.haroncode.wordlearn.common.domain.result.Determinate
import ru.haroncode.wordlearn.common.domain.system.SchedulerProvider
import ru.haroncode.wordlearn.common.domain.validator.ResourceViolation
import ru.haroncode.wordlearn.common.extension.onClick
import ru.haroncode.wordlearn.common.presentation.FlowRouter
import ru.haroncode.wordlearn.common.ui.PublishFragment
import ru.haroncode.wordlearn.common.ui.adapter.DefaultItemClicker
import ru.haroncode.wordlearn.common.ui.adapter.ItemAsyncAdapter
import ru.haroncode.wordlearn.common.ui.adapter.SealedClassViewTypeSelector
import ru.haroncode.wordlearn.common.util.unsafeLazy
import ru.haroncode.wordlearn.word.model.WordExample
import ru.haroncode.wordlearn.wordflow.R
import ru.haroncode.wordlearn.wordflow.presentation.CreateWordConnectionFactory
import ru.haroncode.wordlearn.wordflow.presentation.CreateWordStore.Action
import ru.haroncode.wordlearn.wordflow.presentation.CreateWordViewState
import ru.haroncode.wordlearn.wordflow.presentation.CreateWordViewState.Item
import ru.haroncode.wordlearn.wordflow.presentation.CreateWordViewState.Item.AddButtonItem
import ru.haroncode.wordlearn.wordflow.presentation.CreateWordViewState.Item.WordExampleItem

/**
 * @author HaronCode.
 */
class CreateWordFragment : PublishFragment<Action, CreateWordViewState>(R.layout.fragment_create_word),
    CreateWordExampleDialogFragment.OnCreateExampleListener {

    private val wordExampleAdapter: ItemAsyncAdapter<Item> by unsafeLazy {
        val viewTypeSelector = SealedClassViewTypeSelector.of(WordExampleItem::class, AddButtonItem::class)
        ItemAsyncAdapter.Builder<Item>()
            .withViewTypeSelector(viewTypeSelector::viewTypeFor)
            .add(
                WordExampleItem::class,
                WordExampleAdapterDelegates(),
                DefaultItemClicker { showDialogCreateExample() })
            .add(
                AddButtonItem::class,
                CreateWordAdapterDelegate(),
                DefaultItemClicker(::onClickRemoveExample)
            )
            .build()
    }

    @Inject lateinit var createWordConnectionFactory: CreateWordConnectionFactory
    @Inject lateinit var schedulerProvider: SchedulerProvider
    @Inject lateinit var router: FlowRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StoreViewConnector.withFactory(createWordConnectionFactory)
            .connect(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.setNavigationOnClickListener { router.exit() }

        examplesList.apply {
            itemAnimator = null
            adapter = wordExampleAdapter
        }

        saveButton.onClick { postAction(Action.SaveWord) }

        word.observeTextChanges(Action::ChangeWord)
        transcription.observeTextChanges(Action::ChangeTranscription)
        translation.observeTextChanges(Action::ChangeTranslation)
        association.observeTextChanges(Action::ChangeAssociation)
    }

    override fun onViewStateChanged(state: CreateWordViewState) {
        updateExampleList(state.exampleListItem)

        wordLayout.error = (state.word.verifiable as? ResourceViolation)?.formattedText?.format()
        translationLayout.error = (state.translation.verifiable as? ResourceViolation)?.formattedText?.format()
        saveButton.isEnabled = state.word.isValid &&
                state.translation.isValid &&
                state.determinate !is Determinate.Loading
    }

    private fun onClickRemoveExample(item: WordExampleItem) = item.wordExample
        .let(Action::RemoveExample)
        .let(::postAction)

    private fun updateExampleList(list: List<Item>) = wordExampleAdapter.updateItems(list)

    override fun onCreateWordExample(wordExample: WordExample) = wordExample
        .let(Action::AddExample)
        .let(::postAction)

    private fun showDialogCreateExample() = CreateWordExampleDialogFragment.instance()
        .show(childFragmentManager, CreateWordExampleDialogFragment.TAG)

    override fun onBackPressed() = router.exit()

    override fun onDestroyView() {
        super.onDestroyView()
        examplesList.adapter = null
    }

    private fun EditText.observeTextChanges(
        action: (CharSequence) -> Action
    ) = RxTextView.textChangeEvents(this)
        .skipInitialValue()
        .observeOn(schedulerProvider.computation)
        .map { event -> action(event.text()) }
        .autoDisposable()
        .subscribe(::postAction)
}
