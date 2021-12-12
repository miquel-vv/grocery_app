package com.example.mealplanner.data.model

import java.util.*

data class Link(val full:String, val route:String)
data class MembershipLink(val isOwner:Boolean, val household:Link)
data class Membership(val isOwner: Boolean, val household:Household)
data class MemberLink(val isOwner: Boolean, val user:Link)
data class Member(val isOwner: Boolean, val user:User)

data class User(
    val id:Number,
    val firstName:String,
    val lastName:String,
    val email:String,
    val households:Array<MembershipLink>,
    val url:Link,
    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

data class Household(
    val id:Number,
    val url:Link,
    val name:String,
    val members:Array<MemberLink>,
    val schedules:Array<Link>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Household

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

data class Schedule(
    val id:Number,
    val url: Link,
    val household: Link,
    val name:String
)

data class Meal(
    val id:Number,
    val url: Link,
    val schedule: Link,
    val name:String,
    val date: Date,
    val numberOfPeople:Number,
    val spoontacularId:Number
)