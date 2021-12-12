package com.example.mealplanner.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.example.mealplanner.data.state.HouseholdFilterState
import com.example.mealplanner.databinding.FragmentFilterMealBinding
import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import java.util.*
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import com.example.mealplanner.data.state.DateFilterState
import com.example.mealplanner.data.state.MealState


class FilterMealFragment : Fragment() {

    private lateinit var binding: FragmentFilterMealBinding
    private val householdFilterState = HouseholdFilterState
    private lateinit var startDatePicker : DateTimePicker
    private lateinit var endDatePicker : DateTimePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterMealBinding.inflate(inflater, container, false)
        startDatePicker = DateTimePicker(this.requireContext(), binding.startDate, "START")
        endDatePicker = DateTimePicker(this.requireContext(), binding.endDate, "END")
        fetchHouseholds()
        binding.startDate.setOnClickListener(startDatePicker)
        binding.endDate.setOnClickListener(endDatePicker)
        return binding.root
    }

    private fun fetchHouseholds(){

        householdFilterState.liveHouseholds.observe(viewLifecycleOwner, Observer {
            val households = it?: return@Observer
            val householdNames = (households.map{ h -> h.name }).toMutableList()
            householdNames.add(0, "all")
            val adapter = ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_spinner_item,
                householdNames
            )

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            binding.householdSpinner.adapter = adapter
            binding.householdSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                @Override
                override fun onNothingSelected(item: AdapterView<*>?){
                    householdFilterState.selectedHousehold = null
                }

                @Override
                override fun onItemSelected(item: AdapterView<*>?, view: View?, i: Int, l: Long){
                    if(i==0){
                        householdFilterState.selectedHousehold = null
                    } else {
                        householdFilterState.selectedHousehold = households[i-1]
                    }
                }
            }
        })
    }
}