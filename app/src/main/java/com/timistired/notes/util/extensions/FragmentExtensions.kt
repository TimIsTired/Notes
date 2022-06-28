package com.timistired.notes.util.extensions

import android.content.Context
import android.content.pm.PackageManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
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

fun Fragment.hideKeyboard() {
    requireActivity().run {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let {
            inputMethodManager.hideSoftInputFromWindow(
                it.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
            it.clearFocus()
        }
    }
}

fun Fragment.isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        requireContext(),
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun Fragment.requireResumedState(run: () -> Unit) {
    if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
        run.invoke()
    }
}
