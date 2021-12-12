package com.example.mealplanner.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.marginBottom
import androidx.core.view.setMargins
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.mealplanner.R
import com.example.mealplanner.data.model.Household
import com.example.mealplanner.data.model.Meal
import com.example.mealplanner.data.repo.HouseholdRepository
import com.example.mealplanner.data.state.MealState
import com.example.mealplanner.databinding.FragmentCreateGroceriesBinding
import com.example.mealplanner.ui.MealPageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener


class CreateGroceriesFragment : Fragment() {

    private lateinit var binding:FragmentCreateGroceriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateGroceriesBinding.inflate(inflater, container, false)
        val adapter = MealPageAdapter(parentFragmentManager)
        binding.mealPager.adapter = adapter
        binding.mealTab.getTabAt(0)!!.setText("Meals")
        binding.mealTab.setupWithViewPager(binding.mealPager)
        binding.mealPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(binding.mealTab))
        binding.mealTab.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.mealPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        return binding.root;
    }

}