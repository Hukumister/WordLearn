package ru.coderedwolf.wordlearn.common.ui.adapter

import kotlin.reflect.KClass

/**
 * @author CodeRedWolf. Date 20.10.2019.
 */

class CacheSealedTypeSelector<ItemModel : Any> private constructor(
    private val subclasses: List<KClass<out ItemModel>>
) : ItemModelSealedViewTypeSelector<ItemModel> {

    private val viewTypeCache: MutableMap<KClass<out ItemModel>, Int> = mutableMapOf()

    companion object {

        fun <ItemModel : Any> of(subclasses: List<KClass<out ItemModel>>): CacheSealedTypeSelector<ItemModel> =
            CacheSealedTypeSelector(subclasses.distinct())

        fun <ItemModel : Any> of(vararg subclasses: KClass<out ItemModel>): CacheSealedTypeSelector<ItemModel> =
            CacheSealedTypeSelector(subclasses.distinct().toList())
    }

    override fun invoke(itemModelClass: KClass<out ItemModel>): Int = viewTypeFor(itemModelClass)

    private fun viewTypeFor(itemModelClass: KClass<out ItemModel>): Int = viewTypeCache.getOrPut(itemModelClass) {
        val indexOfFirst = subclasses.indexOfFirst { value -> itemModelClass == value }
        check(indexOfFirst != -1) { "Not found view type for item $itemModelClass" }
        indexOfFirst
    }

}
