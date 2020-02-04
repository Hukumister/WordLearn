package ru.haroncode.wordlearn.wordflow.ui

import androidx.fragment.app.Fragment
import ru.haroncode.wordlearn.common.di.ComponentDependenciesProvider
import ru.haroncode.wordlearn.common.di.HasChildDependencies
import ru.haroncode.wordlearn.common.extension.args
import ru.haroncode.wordlearn.common.ui.FlowFragment
import javax.inject.Inject

/**
 * @author HaronCode.
 */
class WordFlowFragment : FlowFragment(), HasChildDependencies {
    var categoryId: Long by args()
    var categoryName: String by args()

    companion object {
        fun newInstance(categoryId: Long, categoryName: String): Fragment {
            return WordFlowFragment().apply {
                this.categoryId = categoryId
                this.categoryName = categoryName
            }
        }
    }

    @Inject
    override lateinit var dependencies: ComponentDependenciesProvider

    override val launchScreen = WordFlowScreens.WordList
}