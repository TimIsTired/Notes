package com.timistired.notes.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import com.timistired.notes.R
import com.timistired.notes.databinding.FragmentOverviewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by viewModel()
    private lateinit var binding: FragmentOverviewBinding
    private lateinit var overviewAdapter: OverviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        overviewAdapter = OverviewAdapter(onItemSelected = ::onListItemSelected)

        binding.recyclerViewNotes.apply {
            adapter = overviewAdapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.fabCreate.setOnClickListener {
            onCreateSelected()
        }

        viewModel.notePreviews.observe(viewLifecycleOwner) { notePreviews ->
            overviewAdapter.submitList(notePreviews)
        }
    }

    override fun onResume() {
        super.onResume()

        if (!viewModel.coachmarksAlreadyShown) {
            showCoachmarks()
        }
    }

    private fun showCoachmarks() {
        val targetView = binding.fabCreate
        val tapTarget = TapTarget.forView(
            targetView,
            getString(R.string.coachmarks_header),
            getString(R.string.coachmarks_description)
        )
            .cancelable(true)
            .titleTextColor(R.color.white)
            .descriptionTextColor(R.color.white)
            .descriptionTextAlpha(COACH_MARKS_DESCRIPTION_ALPHA)
            .transparentTarget(true)
        TapTargetView.showFor(requireActivity(), tapTarget)

        viewModel.onCoachmarksShown()
    }

    private fun onListItemSelected(id: Long) {
        // TODO navigate to detail screen
    }

    private fun onCreateSelected() {
        findNavController().navigate(R.id.action_overviewFragment_to_createFragment)
    }

    companion object {
        private const val COACH_MARKS_DESCRIPTION_ALPHA = 0.8F
    }
}