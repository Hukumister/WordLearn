package ru.coderedwolf.wordlearn.common.ui.item

import android.content.Context
import android.view.View
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.extensions.LayoutContainer
import ru.coderedwolf.wordlearn.common.util.ContextExtensionsHolder

/**
 * @author CodeRedWolf. Date 10.10.2019.
 */

typealias BaseAdapter = GroupAdapter<GroupieViewHolder>

class BaseViewHolder(
    override val containerView: View
) : com.xwray.groupie.GroupieViewHolder(containerView), LayoutContainer, ContextExtensionsHolder {

    override val extensionContext: Context
        get() = containerView.context
}