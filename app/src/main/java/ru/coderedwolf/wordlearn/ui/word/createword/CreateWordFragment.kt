package ru.coderedwolf.wordlearn.ui.word.createword

import android.os.Bundle
import android.widget.EditText
import com.jakewharton.rxbinding2.widget.RxTextView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_create_word.*
import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.domain.interactors.input.ResourceViolation
import ru.coderedwolf.wordlearn.extension.onClick
import ru.coderedwolf.wordlearn.model.word.WordExample
import ru.coderedwolf.wordlearn.presentation.word.create.CreateWordFeature
import ru.coderedwolf.wordlearn.ui.base.BaseFragment
import ru.coderedwolf.wordlearn.ui.global.MessageDialogFragment
import ru.coderedwolf.wordlearn.ui.word.createword.list.AddExampleItem
import ru.coderedwolf.wordlearn.ui.word.createword.list.WordExampleItem
import ru.coderedwolf.wordlearn.util.HasContextExtensions
import ru.terrakok.cicerone.Router
import toothpick.Scope
import toothpick.Toothpick
import toothpick.config.Module
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 06.06.2019.
 */
class CreateWordFragment : BaseFragment(),
        CreateWordExampleDialogFragment.OnCreateExampleListener,
        HasContextExtensions,
        ObservableSource<UiEvent>,
        Consumer<CreateWordViewModel> {

    private val source = PublishSubject.create<UiEvent>()

    override val layoutRes: Int = R.layout.fragment_create_word

    private val mainSection = Section().apply {
        setFooter(AddExampleItem(::showDialogCreateExample))
    }
    private val wordExampleAdapter = GroupAdapter<ViewHolder>().apply {
        add(mainSection)
    }

    override fun getContextForExtensions() = requireContext()

    override val scopeModuleInstaller = { scope: Scope ->
        scope.installModules(
                object : Module() {
                    init {
                        bind(CreateWordFragment::class.java).toInstance(this@CreateWordFragment)
                        bind(CreateWordFragmentBindings::class.java)
                        bind(CreateWordFeature::class.java)
                    }
                }
        )
    }

    @Inject
    lateinit var router: Router
    @Inject
    lateinit var createWordFragmentBindings: CreateWordFragmentBindings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.setNavigationOnClickListener { router.exit() }
        examplesList.apply {
            itemAnimator = null
            adapter = wordExampleAdapter
        }

        saveButton.onClick { source.onNext(UiEvent.ApplyClicked) }
        createWordFragmentBindings.setup(this)

        listOf(word, translation, transcription, association)
                .forEach(::connect)
    }

    override fun accept(viewModel: CreateWordViewModel) {
        viewModel.error?.let(::showError)
        updateExampleList(viewModel.exampleList)

        wordLayout.error = (viewModel.wordVerify as? ResourceViolation)?.res?.stringRes()
        translationLayout.error = (viewModel.translationVerify as? ResourceViolation)?.res?.stringRes()
        saveButton.isEnabled = viewModel.enableButtonApply
    }

    private fun updateExampleList(list: List<WordExample>) {
        mainSection.update(list.map { example ->
            WordExampleItem(example) { removeExample ->
                removeExample
                        .let(UiEvent::RemoveWordExample)
                        .let(source::onNext)
            }
        })
    }

    override fun onCreateWordExample(wordExample: WordExample) = source
            .onNext(UiEvent.AddWordExample(wordExample))

    private fun showError(error: String) = MessageDialogFragment.create(
            title = getString(R.string.simple_error_title),
            message = error
    ).show(childFragmentManager, "error_dialog")

    private fun showDialogCreateExample() = CreateWordExampleDialogFragment
            .instance()
            .show(childFragmentManager, "create_word")

    override fun onBackPressed() = router.exit()

    override fun onDestroyView() {
        super.onDestroyView()
        examplesList.adapter = null
    }

    override fun subscribe(observer: Observer<in UiEvent>) {
        source.subscribe(observer)
    }

    private fun connect(editText: EditText) = RxTextView.textChangeEvents(editText)
            .skipInitialValue()
            .map(UiEvent::ChangeText)
            .subscribe(source)


}