package com.timistired.notes.ui.overview

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import com.timistired.notes.R
import com.timistired.notes.databinding.FragmentOverviewBinding
import com.timistired.notes.util.TopSpacingDecoration
import com.timistired.notes.util.extensions.loadDimension
import org.koin.androidx.viewmodel.ext.android.viewModel

class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by viewModel()
    private lateinit var binding: FragmentOverviewBinding
    private lateinit var overviewAdapter: OverviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemDecoration = TopSpacingDecoration(
            spacingTop = loadDimension(R.dimen.overview_spacing_top)
        )

        overviewAdapter = OverviewAdapter(onItemSelected = ::onListItemSelected)

        binding.recyclerViewNotes.apply {
            adapter = overviewAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(itemDecoration)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_overview, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.help) {
            showCoachmarks()
            true
        } else {
            super.onOptionsItemSelected(item)
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
        val directions = OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(id)
        // navigate to detail view
        findNavController().navigate(directions)
    }

    private fun onCreateSelected() {
        // navigate to create view
        findNavController().navigate(R.id.action_overviewFragment_to_createFragment)
    }

    companion object {
        private const val COACH_MARKS_DESCRIPTION_ALPHA = 0.8F
    }
}