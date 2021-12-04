package com.example.watchlist2.extension

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.watchlist2.presentation.MainActivity

fun Fragment.setToolbarTitle(@StringRes resId: Int) {
    (activity as? MainActivity)?.supportActionBar?.title = getString(resId)
}