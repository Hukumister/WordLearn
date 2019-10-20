package ru.coderedwolf.wordlearn.common.ui.adapter

import androidx.collection.SparseArrayCompat
import ru.coderedwolf.wordlearn.common.ui.item.BaseAdapter
import ru.coderedwolf.wordlearn.common.ui.item.BaseItem
import ru.coderedwolf.wordlearn.common.ui.item.BaseItemClicker
import ru.coderedwolf.wordlearn.common.ui.item.ComposeItemClicker
import ru.coderedwolf.wordlearn.common.util.putAll
import kotlin.reflect.KClass


/**
 * @author CodeRedWolf. Date 15.10.2019.
 */
typealias ItemModelConverter<ItemModel> = (ItemModel) -> BaseItem

typealias ItemModelViewTypeSelector<ItemModel> = (ItemModel) -> Int

typealias ItemModelSealedViewTypeSelector<ItemModel> = (KClass<out ItemModel>) -> Int

class ItemAdapter<ItemModel : Any>(
    private val clicker: BaseItemClicker<out BaseItem>,
    private val selectorViewType: ItemModelViewTypeSelector<ItemModel>,
    private val converters: SparseArrayCompat<ItemModelConverter<in ItemModel>>
) : BaseAdapter() {

    init {
        setOnItemClickListener(clicker)
    }

    fun swap(itemCollections: Collection<ItemModel>) {
        val items = convertItemModelCollection(itemCollections)
        update(items)
    }

    fun swapAsync(itemCollections: Collection<ItemModel>) {
        val items = convertItemModelCollection(itemCollections).toList()
        updateAsync(items)
    }

    private fun convertItemModelCollection(
        itemCollections: Collection<ItemModel>
    ): Collection<BaseItem> = itemCollections.map { item ->
        val viewType = selectorViewType(item)
        val converter = converters[viewType] ?: throw NoSuchElementException("Not found Converter for item = $item")
        converter(item)
    }

    class Builder<ItemModel : Any> {

        fun withViewSealedClass() = SealedClassesTypesBuilder<ItemModel>()

    }

    class SealedClassesTypesBuilder<ItemModel : Any> {

        private val converters: MutableMap<KClass<out ItemModel>, ItemModelConverter<in ItemModel>> = hashMapOf()
        private var composeClicker: ComposeItemClicker = ComposeItemClicker.none()

        fun add(
            itemModelClass: KClass<out ItemModel>,
            converter: ItemModelConverter<in ItemModel>
        ): SealedClassesTypesBuilder<ItemModel> {
            check(!converters.contains(itemModelClass)) { "Already has converter for $itemModelClass" }
            converters[itemModelClass] = converter
            return this
        }

        fun withClicker(clicker: ComposeItemClicker) {
            composeClicker = clicker
        }

        fun build(): ItemAdapter<ItemModel> {
            val selectorViewType = CacheSealedTypeSelector.of(converters.keys.toList())
            val resultMap = converters
                .mapKeys { entry -> selectorViewType(entry.key) }
            val sparseArrayConverters = SparseArrayCompat<ItemModelConverter<in ItemModel>>()
                .apply { putAll(resultMap) }
            return ItemAdapter(
                clicker = composeClicker,
                selectorViewType = { itemModel -> selectorViewType(itemModel::class) },
                converters = sparseArrayConverters
            )
        }

    }

}
