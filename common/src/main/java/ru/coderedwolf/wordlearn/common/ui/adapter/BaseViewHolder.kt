package ru.coderedwolf.wordlearn.common.ui.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import ru.coderedwolf.wordlearn.common.util.ContextExtensionsHolder

/**
 * @author HaronCode. Date 10.10.2019.
 */

open class BaseViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer, ContextExtensionsHolder {

    override val extensionContext: Context
        get() = containerView.context
}