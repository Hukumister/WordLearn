package ru.coderedwolf.wordlearn.ui.word.createword

import android.os.Bundle
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.fragment_create_word.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.jetbrains.anko.onClick
import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.domain.interactors.validator.Violation
import ru.coderedwolf.wordlearn.presentation.word.create.CreateWordPresenter
import ru.coderedwolf.wordlearn.presentation.word.create.CreateWordView
import ru.coderedwolf.wordlearn.ui.base.BaseFragment
import ru.coderedwolf.wordlearn.ui.global.MessageDialogFragment

/**
 * @author CodeRedWolf. Date 06.06.2019.
 */
class CreateWordFragment : BaseFragment(), CreateWordView {

    override val layoutRes: Int = R.layout.fragment_create_word

    @InjectPresenter
    lateinit var presenter: CreateWordPresenter

    @ProvidePresenter
    fun providePresenter(): CreateWordPresenter =
            scope.getInstance(CreateWordPresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addButton.onClick { presenter.onClickAddExample() }
    }

    override fun showError(error: String) = MessageDialogFragment.create(
            title = "Произошла хрень",
            message = error
    ).show(childFragmentManager, "error_dialog")

    override fun showFieldError(violation: Violation) {

    }

    override fun removeExample(position: Int) {

    }

    override fun addExampleView() {
        val view = LayoutInflater.from(requireContext())
                .inflate(R.layout.item_example_input, null)
        exampleLayout.addView(view, 0)
    }
}