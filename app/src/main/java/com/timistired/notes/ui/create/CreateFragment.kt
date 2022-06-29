package com.timistired.notes.ui.create

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.timistired.notes.R
import com.timistired.notes.databinding.FragmentCreateBinding
import com.timistired.notes.ui.create.CreateUiState.*
import com.timistired.notes.util.extensions.*
import com.timistired.notes.util.locationHelper.ILocationHelper
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateFragment : Fragment() {

    private lateinit var binding: FragmentCreateBinding
    private val locationHelper: ILocationHelper by inject()
    private val viewModel: CreateViewModel by viewModel()
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        if (result.all { it.value }) {
            // permission granted
            captureLocation()
        } else {
            showToast(getString(R.string.location_permission_error_hint))
        }
    }
    private val settingsDialogLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK)
        // location settings enabled via dialog
            viewModel.fetchLocation()
        else {
            showToast(getString(R.string.location_settings_error_hint))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.constraintLayoutCreate.setOnClickListener {
            hideKeyboard()
        }

        binding.buttonLocation.setOnClickListener {
            onFetchLocationClicked()
        }

        binding.fabSave.setOnClickListener {
            onSaveClicked()
        }

        viewModel.uiState.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { uiState ->
                toggleLoadingIndicator(uiState)
                when (uiState) {
                    LOCATION_SUCCESS -> showLocationSuccessIndicators()
                    LOCATION_ERROR -> showLocationErrorToast()
                    NOTE_SAVED -> goBack()
                    else -> {} // ignore
                }
            }
        }
    }

    private fun onFetchLocationClicked() {
        if (isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            captureLocation()
        } else {
            requestLocationPermission()
        }
    }

    private fun onSaveClicked() {
        val headerInput = binding.editTextHeader.text?.toString()?.takeUnless { it.isEmpty() }
        if (headerInput == null) {
            binding.textInputLayoutHeader.error = getString(R.string.header_error)
            return
        }

        val descriptionInput = binding.editTextDescription.text?.toString() ?: ""

        viewModel.save(header = headerInput, description = descriptionInput)
    }

    private fun requestLocationPermission() {
        permissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
    }

    private fun captureLocation() {
        locationHelper.showLocationSettingsDialog(
            activity = requireActivity(),
            successCallback = {
                viewModel.fetchLocation()
            },
            errorCallback = { resolvable ->
                requireResumedState {
                    // show location settings dialog
                    val requestBuilder = IntentSenderRequest.Builder(resolvable.resolution)
                    settingsDialogLauncher.launch(requestBuilder.build())
                }
            })
    }

    private fun showLocationSuccessIndicators() {
        val successText = getString(R.string.location_fetching_success)
        showToast(successText)
        binding.imageViewLocationSuccess.show()
    }

    private fun showLocationErrorToast() {
        val errorText = getString(R.string.location_fetching_error)
        showToast(errorText)
    }

    private fun toggleLoadingIndicator(uiState: CreateUiState) {
        if (uiState == LOADING) {
            binding.constraintLayoutLocationLoadingOverlay.show()
        } else {
            binding.constraintLayoutLocationLoadingOverlay.hide()
        }
    }
}