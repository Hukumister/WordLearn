package ru.coderedwolf.wordlearn.wordflow.ui

import android.os.Bundle
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_create_word.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.coderedwolf.wordlearn.common.domain.validator.Violation
import ru.coderedwolf.wordlearn.common.extension.onClick
import ru.coderedwolf.wordlearn.common.ui.BaseFragment
import ru.coderedwolf.wordlearn.common.ui.MessageDialogFragment
import ru.coderedwolf.wordlearn.word.model.WordExample
import ru.coderedwolf.wordlearn.wordflow.R
import ru.coderedwolf.wordlearn.wordflow.domain.validator.WordValidator
import ru.coderedwolf.wordlearn.wordflow.presentation.CreateWordPresenter
import ru.coderedwolf.wordlearn.wordflow.presentation.CreateWordView
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 06.06.2019.
 */
class CreateWordFragment : BaseFragment(),
    CreateWordView,
    CreateWordExampleDialogFragment.OnClickListener {

    override val layoutRes: Int = R.layout.fragment_create_word

    @Inject
    @InjectPresenter
    lateinit var presenter: CreateWordPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    private val mainSection = Section().apply {
        setFooter(AddExampleItem { presenter.onClickAddExample() })
    }
    private val wordExampleAdapter = GroupAdapter<ViewHolder>().apply {
        add(mainSection)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.setNavigationOnClickListener { presenter.onBackPressed() }
        examplesList.apply {
            itemAnimator = null
            adapter = wordExampleAdapter
        }
        saveButton.onClick {
            presenter.onClickSave(
                word = word.text.toString(),
                translation = translation.text.toString(),
                association = association.text.toString(),
                transcription = transcription.text.toString()
            )
        }
    }

    override fun onCreateWordExample(text: String, translation: String) =
        presenter.onAddWordExample(text, translation)

    override fun updateExampleList(list: List<WordExample>) {
        mainSection.update(list.map { example ->
            WordExampleItem(example) { presenter.onRemoveExample(it) }
        })
    }

    override fun showError(error: String) = MessageDialogFragment.create(
        title = getString(R.string.simple_error_title),
        message = error
    ).show(childFragmentManager, "error_dialog")

    override fun showDialogCreateExample() = CreateWordExampleDialogFragment.instance()
        .show(childFragmentManager, "create_word")

    override fun showFieldError(violation: Violation) {
        translationLayout.error = violation[WordValidator.TRANSLATION_FIELD]
        wordLayout.error = violation[WordValidator.WORD_FIELD]
    }

    override fun onBackPressed() = presenter.onBackPressed()
}