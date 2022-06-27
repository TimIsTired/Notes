package com.timistired.notes.util.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

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

/**
 * Navigate back by popping the backstack.
 * */
fun Fragment.goBack() {
    findNavController().popBackStack()
}