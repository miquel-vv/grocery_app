package com.example.mealplanner.ui.landing

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.R
import com.example.mealplanner.databinding.FragmentLoginBinding
import com.example.mealplanner.ui.main.MainActivity

class LoginFragment : Fragment() {

    private lateinit var viewModel:LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false);

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        setActions(binding)
        return binding.root;
    }

    private fun setActions(binding: FragmentLoginBinding){
        binding.login.setOnClickListener {
            viewModel.loginUser()
            val intent = Intent(this.activity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.username.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setEmail(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setPassword(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

}