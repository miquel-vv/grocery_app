package com.example.mealplanner.ui.households.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mealplanner.data.LoadingStatus
import com.example.mealplanner.databinding.FragmentCreateHouseholdBinding
import com.example.mealplanner.ui.households.viewmodels.CreateHouseholdViewModel
import com.example.mealplanner.ui.households.viewmodels.CreateHouseholdViewModelFactory
import com.example.mealplanner.ui.users.fragments.AddUserFragmentDirections


private const val TAG = "CREATE_HOUSEHOLD_FRAGMENT"


class CreateHouseholdFragment : Fragment() {

    private lateinit var viewModel: CreateHouseholdViewModel
    private lateinit var viewModelFactory: CreateHouseholdViewModelFactory

    private var _binding: FragmentCreateHouseholdBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateHouseholdBinding.inflate(inflater, container, false)

        attachViewModel()
        setUpAction()
        setUpObservable()

        return binding.root
    }

    private fun attachViewModel(){
        viewModelFactory = CreateHouseholdViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(CreateHouseholdViewModel::class.java)
    }

    private fun setUpAction(){
        binding.createHousehold.setOnClickListener {
            val name = binding.householdName.text.toString()
            if (name != "") {
                viewModel.createHousehold(name)
            }
        }
    }

    private fun setUpObservable(){
        viewModel.createHouseholdStatus.observe(viewLifecycleOwner, {status ->
            if(status == LoadingStatus.SUCCESS){
                navigateBackToBrowse()
            } else if(status == LoadingStatus.FAILED) {
                Log.d(TAG, "Couldn't add user...")
            }
        })
    }

    private fun navigateBackToBrowse(){
        findNavController().navigate(
            CreateHouseholdFragmentDirections.actionCreateHouseholdFragmentToBrowseHouseholds()
        )
    }

    override fun onDestroyView() {
        viewModel.resetStatus()
        super.onDestroyView()
        _binding = null
    }
}