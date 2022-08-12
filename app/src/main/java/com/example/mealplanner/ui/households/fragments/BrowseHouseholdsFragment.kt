package com.example.mealplanner.ui.households.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mealplanner.databinding.FragmentBrowseHouseholdsBinding
import com.example.mealplanner.ui.households.viewmodels.BrowseHouseholdsViewModel
import com.example.mealplanner.ui.households.viewmodels.BrowseHouseholdsViewModelFactory


class BrowseHouseholdsFragment : Fragment(), HouseholdAdapter.OnHouseholdListener {

    private var _binding: FragmentBrowseHouseholdsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModelFactory: BrowseHouseholdsViewModelFactory
    private lateinit var viewModel: BrowseHouseholdsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBrowseHouseholdsBinding.inflate(inflater, container, false)

        viewModelFactory = BrowseHouseholdsViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(BrowseHouseholdsViewModel::class.java)
        viewModel.load()

        val adapter = HouseholdAdapter(this)
        binding.households.adapter = adapter
        viewModel.households.observe(viewLifecycleOwner, { hs ->
            adapter.data = hs
            adapter.notifyDataSetChanged()
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(
                BrowseHouseholdsFragmentDirections.actionBrowseHouseholdsToMainMenu()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onHouseholdClick(position: Int) {
        findNavController().navigate(
            BrowseHouseholdsFragmentDirections.actionBrowseHouseholdsToHouseholdView(position)
        )
    }
}