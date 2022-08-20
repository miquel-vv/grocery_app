package com.example.mealplanner.ui.grocerylist.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.data.dao.GroceryListDatabase
import com.example.mealplanner.data.model.GroceryItem
import com.example.mealplanner.data.model.Membership
import com.example.mealplanner.databinding.FragmentGroceryListBinding
import com.example.mealplanner.ui.grocerylist.viewmodels.GroceryListViewModel
import com.example.mealplanner.ui.grocerylist.viewmodels.GroceryListViewModelFactory


class GroceryListFragment : Fragment() {

    private lateinit var viewModel: GroceryListViewModel
    private lateinit var viewModelFactory: GroceryListViewModelFactory

    private var _binding: FragmentGroceryListBinding? = null
    private val binding get() = _binding!!

    private var gotDatabaseData = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroceryListBinding.inflate(inflater, container, false)

        attachViewModel()
        setUpObservable()
        setUpRecyclerView()
        viewModel.getHouseholds()

        return binding.root
    }

    private fun attachViewModel(){
        val application = requireNotNull(this.activity).application
        val dataSource = GroceryListDatabase.getInstance(application).groceryItemDao
        viewModelFactory = GroceryListViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(GroceryListViewModel::class.java)
    }

    private fun setUpRecyclerView(){
        val adapter = GroceryItemAdapter()
        binding.groceryList.adapter = adapter
        viewModel.groceryList.observe(viewLifecycleOwner, {
            gotDatabaseData = true
            adapter.data = it.toList()
            if(viewModel.households.value!!.isNotEmpty()){
                viewModel.updateGroceryList(adapter.data, viewModel.households.value!!)
            }
            adapter.notifyDataSetChanged()
        })
    }

    private fun setUpObservable(){
        viewModel.households.observe(viewLifecycleOwner, {households ->
            if(gotDatabaseData){
                viewModel.updateGroceryList(viewModel.groceryList.value!!, households)
            }
        })
    }

}