package ru.coderedwolf.wordlearn.ui.word

import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.coderedwolf.wordlearn.Screens
import ru.coderedwolf.wordlearn.di.PrimitiveWrapper
import ru.coderedwolf.wordlearn.di.module.FlowNavigationModule
import ru.coderedwolf.wordlearn.di.provider.qualifier.CategoryId
import ru.coderedwolf.wordlearn.di.provider.qualifier.CategoryName
import ru.coderedwolf.wordlearn.extension.args
import ru.coderedwolf.wordlearn.extension.setLaunchScreen
import ru.coderedwolf.wordlearn.ui.base.FlowFragment
import ru.terrakok.cicerone.Router
import toothpick.Scope
import toothpick.Toothpick
import toothpick.config.Module
import javax.inject.Inject

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

    override val scopeModuleInstaller = { scope: Scope ->
        scope.installModules(
                FlowNavigationModule(scope.getInstance(Router::class.java)),
                object : Module() {
                    init {
                        bind(PrimitiveWrapper::class.java)
                                .withName(CategoryId::class.java)
                                .toInstance(PrimitiveWrapper(categoryId))
                        bind(PrimitiveWrapper::class.java)
                                .withName(CategoryName::class.java)
                                .toInstance(PrimitiveWrapper(categoryName))
                    }
                }
        )
    }

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.setLaunchScreen(Screens.WordListScreen)
        }
    }

    override fun onExit() = router.exit()
}