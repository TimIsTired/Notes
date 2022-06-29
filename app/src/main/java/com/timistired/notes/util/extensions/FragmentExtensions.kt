package com.timistired.notes.util.extensions

import android.content.Context
import android.content.pm.PackageManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.timistired.notes.R

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
 * Invokes a given function, if the view lifecycle of this fragment is in 'resumed' state.
 * */
fun Fragment.requireResumedState(run: () -> Unit) {
    if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
        run.invoke()
    }
}

/**
 * Navigate back by popping the backstack.
 * */
fun Fragment.goBack() {
    findNavController().popBackStack()
}

/**
 * Hides the keyboard (if shown).
 * */
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

/**
 * Checks if a given permission is granted.
 *
 * @param permission the permission to check
 * @return true if permission is granted, else false
 * */
fun Fragment.isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        requireContext(),
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

/**
 * Loads a dimension from resources.
 *
 * @param resId the resource id of the dimension to load
 * @return the dimension as integer
 * */
fun Fragment.loadDimension(@DimenRes resId: Int): Int {
    return resources.getDimension(resId).toInt()
}
