package com.example.mealplanner.data

import com.example.mealplanner.data.model.Household
import com.example.mealplanner.data.model.MemberLink
import com.example.mealplanner.data.model.User
import com.example.mealplanner.data.model.Member

object MemberRepository {

    private val members = HashMap<Household, List<Member>>()
    private val memberMapper = HashMap<String, Member>()

    fun getMembers(household: Household):List<Member>{
        val members = mutableListOf<Member>()
        household.members.forEach {memberLink ->
            members.add(getMember(memberLink))
        }

        return members
    }

    private fun getMember(memberLink: MemberLink):Member{
        val users = listOf(
            User(firstName = "John", lastName = "Doe", email = "john.doe@mail.com"),
            User(firstName = "Samantha", lastName = "Smith", email = "s.smith@mail.com"),
            User(firstName = "Sofie", lastName = "Onderbeke", email = "sofie.onderbeke@mail.com"),
        )
        val members = listOf(
            Member(isOwner = true, user = users[0]),
            Member(isOwner = false, user = users[1]),
            Member(isOwner = false, user = users[2])
        )
        val mapper = mapOf("jon" to members[0], "sofie" to members[2], "samantha" to members[1])
        return mapper.getValue(memberLink.user.full)
    }
}