package ru.coderedwolf.wordlearn.mainflow.presentation

import ru.terrakok.cicerone.android.support.SupportAppScreen

interface PhraseTopicReachableFlows {
    fun phraseFlow(id: Long): SupportAppScreen
}