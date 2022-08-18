package com.example.mealplanner.ui.households.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mealplanner.data.LoadingStatus
import com.example.mealplanner.databinding.FragmentHouseholdViewBinding
import com.example.mealplanner.ui.households.viewmodels.HouseholdViewModel
import com.example.mealplanner.ui.households.viewmodels.HouseholdViewModelFactory


private const val TAG = "HOUSEHOLD_VIEW_FRAGMENT"

class HouseholdViewFragment : Fragment(), MemberAdapter.onMemberListener{

    private lateinit var viewModel: HouseholdViewModel
    private lateinit var viewModelFactory: HouseholdViewModelFactory

    private var _binding: FragmentHouseholdViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHouseholdViewBinding.inflate(inflater, container, false)

        attachViewModel()
        setUpMembersRecyclerView()
        setUpSaveListener()
        setButtonActions()

        return binding.root
    }

    private fun attachViewModel(){
        viewModelFactory = HouseholdViewModelFactory(requireArguments().getInt("householdPosition"))
        viewModel = ViewModelProvider(this, viewModelFactory).get(HouseholdViewModel::class.java)

        binding.householdViewModel = viewModel
    }

    private fun setUpMembersRecyclerView(){
        val adapter = MemberAdapter(this)
        binding.members.adapter = adapter
        viewModel.members.observe(viewLifecycleOwner, Observer { members ->
            adapter.isAllowedToEdit = viewModel.isOwner()
            adapter.data = members
            adapter.notifyDataSetChanged()
        })
    }

    private fun setUpSaveListener(){
        viewModel.updateHouseholdStatus.observe(viewLifecycleOwner, Observer { status ->
            if(status == LoadingStatus.SUCCESS){
                goToBrowse()
            }
        })
    }

    private fun setButtonActions(){
        setAddUserAction()
        setCancelButtonAction()
        setSaveButtonAction()
    }

    private fun setAddUserAction(){
        binding.addUser.setOnClickListener {
            findNavController().navigate(
                HouseholdViewFragmentDirections.actionHouseholdViewToAddUserFragment(requireArguments().getInt("householdPosition"))
            )
        }
    }

    private fun setCancelButtonAction(){
        binding.cancelButton.setOnClickListener {
            goToBrowse()
        }
    }

    private fun setSaveButtonAction(){
        binding.saveButton.setOnClickListener {
            val newName = binding.householdName.text.toString()
            if(newName != ""){
                viewModel.updateHousehold(newName)
            } else {
                goToBrowse()
            }
        }
    }

    private fun goToBrowse(){
        findNavController().navigate(
            HouseholdViewFragmentDirections.actionHouseholdViewToBrowseHouseholds()
        )
    }

    override fun onDestroyView() {
        viewModel.resetStatus()
        super.onDestroyView()
        _binding = null
    }

    override fun deleteMember(position: Int) {
        Log.d(TAG, String.format("deleteMember: $position"))
        viewModel.deleteMember(position)
    }

    override fun updateOwnership(position: Int, isOwner: Boolean) {
        Log.d(TAG, "updateOwnership: $position")
        if(isOwner){
            viewModel.makeOwner(position)
        } else {
            viewModel.revokeOwner(position)
        }
    }
}