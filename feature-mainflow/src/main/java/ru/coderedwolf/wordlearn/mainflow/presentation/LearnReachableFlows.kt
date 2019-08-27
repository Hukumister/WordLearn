package ru.coderedwolf.wordlearn.mainflow.presentation

import ru.terrakok.cicerone.android.support.SupportAppScreen

interface LearnReachableFlows {
    fun learnWordsFlow(): SupportAppScreen
    fun learnPhrasesFlow(): SupportAppScreen
}