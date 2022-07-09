package com.example.mealplanner.ui.users.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mealplanner.databinding.FragmentAddUserBinding
import com.example.mealplanner.ui.users.viewmodels.AddUserViewModel
import com.example.mealplanner.ui.users.viewmodels.AddUserViewModelFactory


class AddUserFragment : Fragment(), UserAdapter.OnUserListener {

    private lateinit var viewModel: AddUserViewModel
    private lateinit var viewModelFactory: AddUserViewModelFactory

    private var _binding: FragmentAddUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddUserBinding.inflate(inflater, container, false)

        attachViewModel()
        setUpSearchResults()
        setUpButtonActions()

        return binding.root
    }

    private fun attachViewModel(){
        viewModelFactory = AddUserViewModelFactory(requireArguments().getInt("householdPosition"))
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddUserViewModel::class.java)
    }

    private fun setUpSearchResults(){
        val adapter = UserAdapter(this)
        binding.results.adapter = adapter
        viewModel.searchResults.observe(viewLifecycleOwner, Observer { results ->
            Log.i("AddUserFragment", results.toString())
            adapter.data = results
        })
    }

    private fun setUpButtonActions(){
        binding.search.setOnClickListener {
            viewModel.searchByEmail("something")
        }
    }

    override fun onUserClick(position: Int) {
        findNavController().navigate(
            AddUserFragmentDirections.actionAddUserFragmentToHouseholdView(viewModel.position)
        )
    }
}