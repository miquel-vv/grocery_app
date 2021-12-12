package com.example.mealplanner.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.mealplanner.ui.main.FilterMealFragment
import com.example.mealplanner.ui.main.MealOverviewFragment

class MealPageAdapter(manager:FragmentManager): FragmentPagerAdapter(manager,  BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val titles = listOf("Meals", "Filter")

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return MealOverviewFragment()
            1 -> return FilterMealFragment()
        }
        return MealOverviewFragment()
    }
}