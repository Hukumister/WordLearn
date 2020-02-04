package ru.haroncode.wordlearn.mainflow.presentation

import ru.terrakok.cicerone.android.support.SupportAppScreen

interface PhraseTopicReachableFlows {
    fun phraseFlow(id: Long): SupportAppScreen
}
