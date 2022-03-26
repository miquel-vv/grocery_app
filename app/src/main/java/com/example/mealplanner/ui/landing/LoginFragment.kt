package com.example.mealplanner.ui.landing

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mealplanner.R
import com.example.mealplanner.databinding.FragmentLoginBinding
import com.example.mealplanner.ui.main.MainActivity

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false);
        binding.login.setOnClickListener {
            val intent = Intent(this.activity, MainActivity::class.java)
            startActivity(intent)
        }

        return binding.root;
    }

}