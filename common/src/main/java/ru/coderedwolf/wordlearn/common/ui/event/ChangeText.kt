package ru.coderedwolf.wordlearn.common.ui.event

/**
 * @author CodeRedWolf. Date 22.09.2019.
 */
data class ChangeText(
        override val elementId: Int,
        val text: CharSequence
) : UiEvent(elementId)