package com.timistired.notes.util.extensions

import android.view.View

/**
 * Show this view
 * */
fun View.show() {
    visibility = View.VISIBLE
}

/**
 * Hide this view
 * */
fun View.hide() {
    visibility = View.GONE
}