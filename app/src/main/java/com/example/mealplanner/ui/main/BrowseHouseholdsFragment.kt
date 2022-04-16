package com.example.mealplanner.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mealplanner.R
import com.example.mealplanner.data.model.Household
import com.example.mealplanner.data.model.Link
import com.example.mealplanner.data.model.Member
import com.example.mealplanner.databinding.FragmentBrowseHouseholdsBinding


class BrowseHouseholdsFragment : Fragment() {

    private var _binding: FragmentBrowseHouseholdsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBrowseHouseholdsBinding.inflate(inflater, container, false)

        val householdData = listOf(Household(name = "household1"),Household(name = "household2"),Household(name = "household3"))
        val adapter = HouseholdAdapter()
        binding.households.adapter = adapter
        adapter.data = householdData
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_BrowseHouseholds_to_MainMenu)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}