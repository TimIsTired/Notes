package com.timistired.notes.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.timistired.notes.R
import com.timistired.notes.databinding.FragmentCreateBinding
import com.timistired.notes.ui.create.CreateUiStatus.*
import com.timistired.notes.util.extensions.goBack
import com.timistired.notes.util.extensions.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateFragment : Fragment() {

    private val viewModel: CreateViewModel by viewModel()
    private lateinit var binding: FragmentCreateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO implement UI and init here

        viewModel.uiStatus.observe(viewLifecycleOwner) { uiStatus ->
            toggleLoadingIndicator(uiStatus)
            when (uiStatus) {
                LOCATION_SUCCESS -> showLocationSuccessToast()
                LOCATION_ERROR -> showLocationErrorToast()
                GO_BACK -> goBack()
                else -> {} // ignore
            }
        }
    }

    private fun showLocationSuccessToast() {
        val successText = getString(R.string.location_fetching_success)
        showToast(successText)
    }

    private fun showLocationErrorToast() {
        val errorText = getString(R.string.location_fetching_error)
        showToast(errorText)
    }

    private fun toggleLoadingIndicator(uiStatus: CreateUiStatus) {
        if (uiStatus == LOADING) {
            // TODO show loading indicator
        } else {
            // TODO hide loading indicator
        }
    }
}