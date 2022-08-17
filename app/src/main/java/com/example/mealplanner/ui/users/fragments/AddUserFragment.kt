package com.example.mealplanner.ui.users.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mealplanner.data.LoadingStatus
import com.example.mealplanner.databinding.FragmentAddUserBinding
import com.example.mealplanner.ui.users.viewmodels.AddUserViewModel
import com.example.mealplanner.ui.users.viewmodels.AddUserViewModelFactory

private const val TAG = "ADD_USER_FRAGMENT"

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
        setUpObservables()
        setUpButtonActions()

        return binding.root
    }

    private fun attachViewModel(){
        viewModelFactory = AddUserViewModelFactory(requireArguments().getInt("householdPosition"))
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddUserViewModel::class.java)
    }

    private fun setUpObservables(){
        setUpSearchResults()
        setUpAddingMember()
    }

    private fun setUpSearchResults(){
        viewModel.userId.observe(viewLifecycleOwner, Observer { result ->
            Log.i(TAG, result.toString())
            viewModel.addUserToHousehold(result)
        })
    }

    private fun setUpAddingMember(){
        viewModel.addingMemberStatus.observe(viewLifecycleOwner, {status ->
            if(status == LoadingStatus.SUCCESS){
                navigateBackToHousehold()
            } else if(status == LoadingStatus.FAILED) {
                Log.d(TAG, "Couldn't add user...")
            }
        })
    }

    private fun setUpButtonActions(){
        binding.search.setOnClickListener {
            viewModel.searchByEmail(binding.userEmail.text.toString())
        }
    }

    override fun onUserClick(position: Int) {
        navigateBackToHousehold()
    }

    private fun navigateBackToHousehold() {
        val bundle = bundleOf("householdPosition" to viewModel.position)

        /*val fragmentTransaction = parentFragmentManager.beginTransaction()
        val householdView = HouseholdViewFragment()
        householdView.arguments = bundle
        fragmentTransaction.add(householdView, "Test")
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()*/

        findNavController().navigate(
            AddUserFragmentDirections.actionAddUserFragmentToHouseholdView(viewModel.position)
        )
    }
}