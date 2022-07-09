package com.example.mealplanner.data

import com.example.mealplanner.data.model.Household
import com.example.mealplanner.data.model.Link
import com.example.mealplanner.data.model.MemberLink

object HouseholdRepository {

    private lateinit var households:List<Household>

    init {
        fetchHouseholds()
    }

    private fun fetchHouseholds(){

        val members = arrayOf(
            MemberLink(true, Link("jon", "jon")),
            MemberLink(false, Link("sofie", "sofie")),
            MemberLink(false, Link("samantha", "samantha"))
        )

        households = listOf(
            Household(name = "household1", members = members),
            Household(name = "household2", members = members),
            Household(name = "household3", members = members)
        )
    }

    fun getHouseholds():List<Household> {
        if(!this::households.isInitialized){
            fetchHouseholds()
        }
        return households
    }
}