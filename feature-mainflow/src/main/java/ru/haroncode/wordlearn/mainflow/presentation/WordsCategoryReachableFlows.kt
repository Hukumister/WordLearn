package ru.haroncode.wordlearn.mainflow.presentation

import ru.terrakok.cicerone.android.support.SupportAppScreen

interface WordsCategoryReachableFlows {
    fun wordFlow(categoryId: Long, categoryName: String): SupportAppScreen
}
