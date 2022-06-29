package com.example.mealplanner.ui.households.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.databinding.FragmentHouseholdViewBinding
import com.example.mealplanner.ui.households.viewmodels.HouseholdViewModel
import com.example.mealplanner.ui.households.viewmodels.HouseholdViewModelFactory


class HouseholdViewFragment : Fragment() {

    private lateinit var viewModel: HouseholdViewModel
    private lateinit var viewModelFactory: HouseholdViewModelFactory

    private var _binding: FragmentHouseholdViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHouseholdViewBinding.inflate(inflater, container, false)

        viewModelFactory = HouseholdViewModelFactory(requireArguments().getInt("householdPosition"))
        viewModel = ViewModelProvider(this, viewModelFactory).get(HouseholdViewModel::class.java)

        binding.householdViewModel = viewModel

        val adapter = MemberAdapter()
        binding.members.adapter = adapter
        viewModel.members.observe(viewLifecycleOwner, Observer { members ->
            adapter.data = members
            adapter.notifyDataSetChanged()
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}