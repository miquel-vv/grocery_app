package com.example.mealplanner.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.data.LoadingStatus
import com.example.mealplanner.databinding.FragmentBrowseHouseholdsBinding
import com.example.mealplanner.databinding.FragmentLoginBinding
import com.example.mealplanner.ui.main.MainActivity

class LoginFragment : Fragment() {

    private lateinit var viewModelFactory: LoginViewModelFactory
    private lateinit var viewModel: LoginViewModel

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        viewModelFactory = LoginViewModelFactory(requireActivity().getSharedPreferences("token_data", Context.MODE_PRIVATE))
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        setActions()
        createObservation()

        return binding.root
    }

    private fun createObservation() {
        val observer = Observer<LoadingStatus> { status ->
            if(status == LoadingStatus.SUCCESS) {
                val intent = Intent(this.activity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else if(status == LoadingStatus.LOADING) {
                Log.d("LOGIN_FRAGMENT", "Logging in...")
                binding.login.isEnabled = false
                binding.username.isEnabled = false
                binding.password.isEnabled = false
            } else if(status == LoadingStatus.FAILED) {
                Log.d("LOGIN_FRAGMENT", "Failed logging in...")
                binding.login.isEnabled = true
                binding.login.isClickable = true
                binding.login.isEnabled = true
            }
        }
        viewModel.loadingStatus.observe(viewLifecycleOwner, observer)
    }

    private fun setActions(){
        binding.login.setOnClickListener {
            viewModel.loginUser()
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