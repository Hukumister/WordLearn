package ru.coderedwolf.wordlearn.wordflow.ui

import androidx.fragment.app.Fragment
import ru.coderedwolf.wordlearn.common.extension.args
import ru.coderedwolf.wordlearn.common.ui.FlowFragment

/**
 * @author CodeRedWolf. Date 04.06.2019.
 */
class WordFlowFragment : FlowFragment() {
    private var categoryId: Long by args()
    private var categoryName: String by args()

    companion object {
        fun newInstance(categoryId: Long, categoryName: String): Fragment {
            return WordFlowFragment().apply {
                this.categoryId = categoryId
                this.categoryName = categoryName
            }
        }
    }

    override val launchScreen = WordFlowScreens.WordList
}