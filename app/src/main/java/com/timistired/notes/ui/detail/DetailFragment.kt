package com.timistired.notes.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.timistired.notes.data.model.Location
import com.timistired.notes.data.model.NoteFull
import com.timistired.notes.databinding.FragmentDetailBinding
import com.timistired.notes.util.extensions.show
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
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            init(args.noteId)
            note.observe(viewLifecycleOwner) { noteFull ->
                showNoteData(noteFull)
            }
        }
    }

    private fun showNoteData(noteFull: NoteFull) {
        with(binding) {
            textViewHeader.text = noteFull.header
            textViewDescription.text = noteFull.description
            noteFull.location?.let { location ->
                buttonShowLocation.show()
                buttonShowLocation.setOnClickListener {
                    showLocationOnMap(location)
                }
            }
        }
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
}