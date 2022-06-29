package com.timistired.notes.ui.detail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.timistired.notes.R
import com.timistired.notes.data.model.Location
import com.timistired.notes.databinding.FragmentDetailBinding
import com.timistired.notes.util.FadeInAnimator
import com.timistired.notes.util.extensions.goBack
import com.timistired.notes.util.extensions.hide
import com.timistired.notes.util.extensions.show
import com.timistired.notes.util.extensions.showToast
import com.timistired.notes.util.locationHelper.ILocationHelper
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModel()
    private val locationHelper: ILocationHelper by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            init(args.noteId)

            header.observe(viewLifecycleOwner) { header ->
                binding.textViewHeader.text = header
            }

            description.observe(viewLifecycleOwner) { description ->
                binding.textViewDescription.text = description
            }

            location.observe(viewLifecycleOwner) { location ->
                binding.buttonShowLocation.show()
                binding.buttonShowLocation.setOnClickListener {
                    showLocationOnMap(location)
                }
            }

            date.observe(viewLifecycleOwner) { date ->
                binding.textViewDate.text = date
            }

            uiState.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { uiState ->
                    toggleLoadingIndicator(uiState)
                    when (uiState) {
                        DetailUiState.NOTE_DELETED -> {
                            showDeletedToast()
                            goBack()
                        }
                        else -> {} // ignore
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fadeInViews()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.delete) {
            viewModel.deleteNote()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun toggleLoadingIndicator(uiState: DetailUiState) {
        if (uiState == DetailUiState.LOADING) {
            binding.loadingSpinnerDetail.show()
        } else {
            binding.loadingSpinnerDetail.hide()
        }
    }

    private fun fadeInViews() {
        val views = listOf(
            binding.textViewDate,
            binding.textViewHeader,
            binding.textViewDescription,
            binding.buttonShowLocation
        )

        // fade in views one by one
        FadeInAnimator(
            targets = views,
            duration = FADE_IN_DURATION,
            lifecycleOwner = viewLifecycleOwner
        )
    }

    private fun showDeletedToast() {
        showToast(getString(R.string.note_deleted))
    }

    private fun showLocationOnMap(location: Location) {
        val isGoogleMapsAvailable = locationHelper.isGoogleMapsAvailable(requireContext())
        val mapsIntent = if (isGoogleMapsAvailable) {
            // google maps available, show location there
            locationHelper.getGoogleMapsIntent(location)
        } else {
            // google maps not available, let the user choose a maps-app as fallback
            locationHelper.getGenericMapsIntent(location)
        }

        startActivity(mapsIntent)
    }

    companion object {
        private const val FADE_IN_DURATION = 700L
    }
}