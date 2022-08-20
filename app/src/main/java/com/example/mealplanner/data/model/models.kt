package com.example.mealplanner.data.model

import androidx.room.ColumnInfo
import java.util.*
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Link(val full:String, val route:String)
data class MembershipLink(val isOwner:Boolean, val household:Link)
data class Membership(val isOwner: Boolean, val household:Household)
data class MemberLink(val isOwner: Boolean, val user:Link)
data class Member(val isOwner: Boolean, val user:User)

data class User(
    val id:Number = Int.MIN_VALUE,
    val firstName:String,
    val lastName:String,
    val email:String,
    val households:Array<MembershipLink> = arrayOf(),
    val url:Link = Link("s", "s"),
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
    val id:Number = Int.MIN_VALUE,
    val url:Link = Link("s", "s"),
    val name:String,
    val members:Array<MemberLink> = arrayOf(),
    val schedules:Array<Link> = arrayOf()
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
    val spoontacularId:Number?
)

enum class GroceryItemStatus{
    OPEN, SCRAPPED, REMOVED
}

@Entity(tableName = "grocery_items")
data class GroceryItem(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    @ColumnInfo(name="spoon_id")
    val spoonId:Long,
    @ColumnInfo(name="name")
    val name:String,
    @ColumnInfo(name="amount")
    var amount:Float,
    @ColumnInfo(name="unit")
    val unit:String,
    @ColumnInfo(name="status")
    val status:GroceryItemStatus) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GroceryItem

        if (id == other.id && status == other.status) return true

        return false
    }

    override fun hashCode(): Int {
        return id.hashCode() * 7 + status.hashCode()
    }
}
