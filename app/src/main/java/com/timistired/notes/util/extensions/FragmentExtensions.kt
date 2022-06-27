package com.timistired.notes.util.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Shows a [Toast] for a given text.
 *
 * @param text the text to show to the user
 * */
fun Fragment.showToast(text: String) {
    Toast(requireActivity()).apply {
        duration = Toast.LENGTH_LONG
        setText(text)
    }.show()
}