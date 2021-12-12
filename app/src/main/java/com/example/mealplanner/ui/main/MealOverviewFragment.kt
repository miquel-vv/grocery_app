package com.example.mealplanner.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.mealplanner.R
import com.example.mealplanner.databinding.FragmentMealOverviewBinding


import androidx.core.view.setMargins
import com.example.mealplanner.data.model.Meal
import com.example.mealplanner.data.state.HouseholdFilterState
import com.example.mealplanner.data.state.MealState
import com.example.mealplanner.databinding.FragmentCreateGroceriesBinding


class MealOverviewFragment : Fragment() {

    private lateinit var binding: FragmentMealOverviewBinding
    private val mealState = MealState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealOverviewBinding.inflate(inflater, container, false)
        binding.buildGroceries.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_createGroceriesFragment_to_groceryListFragment))
        fetchMeals()
        return binding.root
    }

    fun fetchMeals(){
        mealState.liveMeals.observe(viewLifecycleOwner, Observer {
            val meals = it ?: return@Observer
            clearList()
            for (meal in meals) {
                addMeal(meal)
            }
        })
    }

    private fun clearList(){
        binding.mealsView.removeAllViews()
    }

    private fun addMeal(meal: Meal){
        val tableRow = createTableRow()
        val mealName = createMealName(meal.name)
        tableRow.addView(mealName)
        binding.mealsView.addView(tableRow)
    }

    private fun createTableRow(): TableRow {
        val tableRow = TableRow(binding.mealsView.context)
        tableRow.setBackgroundColor(resources.getColor(R.color.design_default_color_secondary_variant, resources.newTheme()))
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(resources.getDimensionPixelSize(R.dimen.margin_normal))
        tableRow.layoutParams = layoutParams
        return tableRow
    }

    private fun createMealName(name:String): TextView {
        val mealName = TextView(binding.mealsView.context)
        mealName.setTextAppearance(R.style.meal_name)
        val layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(resources.getDimensionPixelSize(R.dimen.margin_normal))
        mealName.layoutParams = layoutParams
        mealName.text = name
        return mealName
    }

}