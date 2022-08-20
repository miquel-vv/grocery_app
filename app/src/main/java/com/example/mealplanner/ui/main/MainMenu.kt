package com.example.mealplanner.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mealplanner.R
import com.example.mealplanner.databinding.MainMenuBinding


class MainMenu : Fragment() {

    private var _binding: MainMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("MainMenu", "created main screen.")
        _binding = MainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.manageHouseholds.setOnClickListener {
            findNavController().navigate(R.id.action_MainMenu_to_BrowseHousholds)
        }

        binding.manageGroceryList.setOnClickListener {
            findNavController().navigate(R.id.action_MainMenu_to_groceryListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}