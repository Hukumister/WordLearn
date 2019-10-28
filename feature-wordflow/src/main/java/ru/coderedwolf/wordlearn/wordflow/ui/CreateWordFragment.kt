package ru.coderedwolf.wordlearn.wordflow.ui

import android.os.Bundle
import android.widget.EditText
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_create_word.*
import ru.coderedwolf.wordlearn.common.domain.system.SchedulerProvider
import ru.coderedwolf.wordlearn.common.domain.validator.ResourceViolation
import ru.coderedwolf.wordlearn.common.extension.onClick
import ru.coderedwolf.wordlearn.common.presentation.FlowRouter
import ru.coderedwolf.wordlearn.common.ui.BaseFragment
import ru.coderedwolf.wordlearn.common.ui.adapter.DefaultClicker
import ru.coderedwolf.wordlearn.common.ui.adapter.ItemAsyncAdapter
import ru.coderedwolf.wordlearn.common.ui.event.ChangeText
import ru.coderedwolf.wordlearn.common.ui.event.UiEvent
import ru.coderedwolf.wordlearn.common.util.ContextExtensionsHolder
import ru.coderedwolf.wordlearn.word.model.WordExample
import ru.coderedwolf.wordlearn.wordflow.R
import ru.coderedwolf.wordlearn.wordflow.presentation.*
import ru.coderedwolf.wordlearn.wordflow.presentation.CreateWordViewModel.Item
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 06.06.2019.
 */
class CreateWordFragment : BaseFragment(),
    CreateWordExampleDialogFragment.OnCreateExampleListener,
    ObservableSource<UiEvent>,
    Consumer<CreateWordViewModel> {

    private val source = PublishSubject.create<UiEvent>()

    override val layoutRes: Int = R.layout.fragment_create_word


    private lateinit var wordExampleAdapter: ItemAsyncAdapter<Item>

    @Inject
    lateinit var schedulerProvider: SchedulerProvider
    @Inject
    lateinit var router: FlowRouter
    @Inject
    lateinit var createWordFeature: CreateWordFeature

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

        saveButton.onClick { source.onNext(SaveClick) }
        listOf(word, translation, transcription, association)
            .forEach(::connect)

        CreateWordFragmentBindings(this, createWordFeature).setup(this)
    }

    private fun onClickRemoveExample(item: Item.WordExampleItem) = item.wordExample
        .let(::RemoveWordExample)
        .let(source::onNext)

    override fun accept(viewModel: CreateWordViewModel) {
        updateExampleList(viewModel.exampleList)

        wordLayout.error = (viewModel.wordVerify as? ResourceViolation)?.res?.resString()
        translationLayout.error = (viewModel.translationVerify as? ResourceViolation)?.res?.resString()
        saveButton.isEnabled = viewModel.enableButtonApply
    }

    private fun updateExampleList(list: List<Item>) = wordExampleAdapter.updateItems(list)

    override fun onCreateWordExample(wordExample: WordExample) = source
        .onNext(AddWordExample(wordExample))

    private fun showDialogCreateExample() = CreateWordExampleDialogFragment.instance()
        .show(childFragmentManager, CreateWordExampleDialogFragment.TAG)

    override fun onBackPressed() = router.exit()

    override fun onDestroyView() {
        super.onDestroyView()
        examplesList.adapter = null
    }

    override fun subscribe(observer: Observer<in UiEvent>) = source.subscribe(observer)

    private fun connect(editText: EditText) = RxTextView.textChangeEvents(editText)
        .skipInitialValue()
        .observeOn(schedulerProvider.computation)
        .map { event -> ChangeText(event.view().id, event.text()) }
        .autoDisposable()
        .subscribe(source)

}