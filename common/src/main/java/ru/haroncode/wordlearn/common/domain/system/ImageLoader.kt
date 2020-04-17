package ru.haroncode.wordlearn.common.domain.system

import android.widget.ImageView
import ru.haroncode.wordlearn.common.domain.model.Ticker

interface ImageLoader {

    fun loadTicker(ticker: Ticker, imageView: ImageView)
}
