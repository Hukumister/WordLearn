package ru.coderedwolf.wordlearn.common.ui.event

/**
 * @author CodeRedWolf. Date 22.09.2019.
 */
data class ButtonClick(
        override val elementId: Int
) : UiEvent(elementId)
