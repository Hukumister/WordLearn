package ru.coderedwolf.wordlearn.common.util

import androidx.annotation.IdRes
import ru.terrakok.cicerone.android.support.SupportAppScreen

inline infix fun SupportAppScreen.into(@IdRes containerId: Int): Change =
    Change(containerId, this)