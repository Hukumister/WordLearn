package ru.coderedwolf.wordlearn.common.ui.item

import android.view.View
import com.xwray.groupie.OnItemClickListener
import kotlin.reflect.KClass

/**
 * @author CodeRedWolf. Date 10.10.2019.
 */

class ComposeItemClicker private constructor(
    private val clickerMap: Map<KClass<*>, OnItemClickListener>
) : BaseItemClicker<BaseItem>() {

    companion object {

        fun none() = ComposeItemClicker(emptyMap())
    }

    class Builder {

        private val clickerMap: MutableMap<KClass<*>, OnItemClickListener> = mutableMapOf()

        fun <T : BaseItem> add(itemClass: KClass<T>, baseItemClicker: BaseItemClicker<T>): Builder {
            clickerMap[itemClass] = baseItemClicker
            return this
        }

        fun build() = ComposeItemClicker(clickerMap)

    }

    override fun onItemClick(item: BaseItem, view: View) {
        clickerMap[item::class]?.onItemClick(item, view)
    }

}

