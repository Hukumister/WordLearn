package ru.haroncode.wordlearn.common.ui.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import ru.haroncode.wordlearn.common.util.ContextExtensionsHolder

open class BaseViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer, ContextExtensionsHolder {

    override val extensionContext: Context
        get() = containerView.context
}
