package com.example.mealplanner.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.data.LoadingStatus
import com.example.mealplanner.databinding.FragmentRegisterBinding
import com.example.mealplanner.ui.main.MainActivity


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModelFactory: RegisterViewModelFactory
    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        addViewModels()
        setUpActions()
        setUpObservation()

        return binding.root
    }

    private fun addViewModels(){
        viewModelFactory = RegisterViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(RegisterViewModel::class.java)
    }

    private fun setUpActions(){
        binding.register.setOnClickListener {
            val email = binding.email.text.toString()
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()
            if(!viewModel.registerUser(email, username, password, confirmPassword)){
                val toast = Toast.makeText(context, "Passwords don't match", Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }

    private fun setUpObservation(){
        viewModel.registerStatus.observe(viewLifecycleOwner, {
            when(it){
                LoadingStatus.SUCCESS -> {
                    val intent = Intent(this.activity, com.example.mealplanner.ui.landing.MainActivity::class.java)
                    intent.putExtra("REGISTERED", true)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                LoadingStatus.LOADING -> {
                    binding.email.isEnabled = false
                    binding.username.isEnabled = false
                    binding.password.isEnabled = false
                    binding.confirmPassword.isEnabled = false
                }
                LoadingStatus.FAILED -> {
                    binding.email.isEnabled = true
                    binding.username.isEnabled = true
                    binding.password.isEnabled = true
                    binding.confirmPassword.isEnabled = true
                    val toast = Toast.makeText(context, "Failed to register user", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        })
    }

}