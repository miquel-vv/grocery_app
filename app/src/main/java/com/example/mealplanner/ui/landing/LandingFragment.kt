package com.example.mealplanner.ui.landing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.mealplanner.R
import com.example.mealplanner.databinding.FragmentLandingBinding


class LandingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentLandingBinding = FragmentLandingBinding.inflate(inflater, container, false)

        binding.loginButton.setOnClickListener {view: View ->
            view.findNavController().navigate(R.id.action_landingFragment_to_loginFragment)
        }

        binding.registerButton.setOnClickListener {view: View ->
            view.findNavController().navigate(R.id.action_landingFragment_to_registerFragment)
        }
        return binding.root
    }
}