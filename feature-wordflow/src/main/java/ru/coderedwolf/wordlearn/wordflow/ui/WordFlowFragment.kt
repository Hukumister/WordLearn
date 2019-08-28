package ru.coderedwolf.wordlearn.wordflow.ui

import androidx.fragment.app.Fragment
import ru.coderedwolf.wordlearn.common.di.ComponentDependenciesProvider
import ru.coderedwolf.wordlearn.common.di.HasChildDependencies
import ru.coderedwolf.wordlearn.common.extension.args
import ru.coderedwolf.wordlearn.common.ui.FlowFragment
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 04.06.2019.
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