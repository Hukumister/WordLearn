package ru.haroncode.wordlearn.common.ui.adapter

import android.util.SparseArray
import androidx.core.util.containsKey
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import kotlin.reflect.KClass

class ItemAdapterDelegatesManager<ItemModel : Any>(
    private val sealedClassViewTypeSelector: (KClass<out ItemModel>) -> Int
) : AdapterDelegatesManager<List<ItemModel>>() {

    private val sparseArray = SparseArray<AdapterDelegate<List<ItemModel>>>()

    fun addDelegateByClass(
        kClass: KClass<out ItemModel>,
        delegate: AdapterDelegate<List<ItemModel>>
    ): Int {
        val viewType = sealedClassViewTypeSelector.invoke(kClass)
        check(!sparseArray.containsKey(viewType)) { "Already has delegate for viewType=$viewType" }
        sparseArray.put(viewType, delegate)
        super.addDelegate(viewType, delegate)
        return viewType
    }

    override fun addDelegate(
        viewType: Int,
        delegate: AdapterDelegate<List<ItemModel>>
    ): AdapterDelegatesManager<List<ItemModel>> {
        sparseArray.put(viewType, delegate)
        return super.addDelegate(viewType, delegate)
    }

    override fun addDelegate(
        viewType: Int,
        allowReplacingDelegate: Boolean,
        delegate: AdapterDelegate<List<ItemModel>>
    ): AdapterDelegatesManager<List<ItemModel>> {
        sparseArray.put(viewType, delegate)
        return super.addDelegate(viewType, allowReplacingDelegate, delegate)
    }

    override fun removeDelegate(viewType: Int): AdapterDelegatesManager<List<ItemModel>> {
        sparseArray.remove(viewType)
        return super.removeDelegate(viewType)
    }

    override fun addDelegate(delegate: AdapterDelegate<List<ItemModel>>): AdapterDelegatesManager<List<ItemModel>> {
        throw UnsupportedOperationException("Use addDelegateByClass method instead")
    }

    override fun getItemViewType(items: List<ItemModel>, position: Int): Int {
        val itemModel = items[position]
        return sealedClassViewTypeSelector(itemModel::class)
    }

    override fun getViewType(delegate: AdapterDelegate<List<ItemModel>>) = sparseArray.indexOfValue(delegate)

    override fun getDelegateForViewType(viewType: Int): AdapterDelegate<List<ItemModel>> = sparseArray.get(viewType)
}
